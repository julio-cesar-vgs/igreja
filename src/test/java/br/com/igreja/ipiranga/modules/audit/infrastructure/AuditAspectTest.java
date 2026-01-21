package br.com.igreja.ipiranga.modules.audit.infrastructure;

import br.com.igreja.ipiranga.infrastructure.security.CustomUserDetails;
import br.com.igreja.ipiranga.modules.audit.domain.model.LogCorrecao;
import br.com.igreja.ipiranga.modules.audit.domain.repository.LogCorrecaoRepository;
import br.com.igreja.ipiranga.modules.identity.domain.model.Usuario;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditAspectTest {

    @Mock
    private LogCorrecaoRepository logRepository;

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private Signature signature;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuditAspect auditAspect;

    private Usuario usuario;
    private CustomUserDetails userDetails;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .email("test@example.com")
                .nome("Test User")
                .senha("password")
                .role(Usuario.Role.ROLE_USER)
                .build();

        userDetails = new CustomUserDetails(usuario);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void logAfterWrite_WithSaveMethod_ShouldLogInsertUpdate() {
        // Arrange
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("saveUsuario");
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Object result = new Object();
        ArgumentCaptor<LogCorrecao> logCaptor = ArgumentCaptor.forClass(LogCorrecao.class);

        // Act
        auditAspect.logAfterWrite(joinPoint, result);

        // Assert
        verify(logRepository, times(1)).save(logCaptor.capture());
        LogCorrecao savedLog = logCaptor.getValue();

        assertThat(savedLog.getAcao()).isEqualTo("INSERT/UPDATE");
        assertThat(savedLog.getUsuarioId()).isEqualTo(1L);
        assertThat(savedLog.getEntidadeTipo()).isEqualTo("Object");
        assertThat(savedLog.getTimestamp()).isNotNull();
    }

    @Test
    void logAfterWrite_WithDeleteMethod_ShouldLogDelete() {
        // Arrange
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("deleteCulto");
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Object result = new Object();
        ArgumentCaptor<LogCorrecao> logCaptor = ArgumentCaptor.forClass(LogCorrecao.class);

        // Act
        auditAspect.logAfterWrite(joinPoint, result);

        // Assert
        verify(logRepository, times(1)).save(logCaptor.capture());
        LogCorrecao savedLog = logCaptor.getValue();

        assertThat(savedLog.getAcao()).isEqualTo("DELETE");
        assertThat(savedLog.getUsuarioId()).isEqualTo(1L);
    }

    @Test
    void logAfterWrite_WithoutAuthentication_ShouldLogWithNullUserId() {
        // Arrange
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("saveOferta");

        Object result = new Object();
        ArgumentCaptor<LogCorrecao> logCaptor = ArgumentCaptor.forClass(LogCorrecao.class);

        // Act
        auditAspect.logAfterWrite(joinPoint, result);

        // Assert
        verify(logRepository, times(1)).save(logCaptor.capture());
        LogCorrecao savedLog = logCaptor.getValue();

        assertThat(savedLog.getUsuarioId()).isNull();
        assertThat(savedLog.getAcao()).isEqualTo("INSERT/UPDATE");
    }

    @Test
    void logAfterWrite_WithNullResult_ShouldLogWithUnknownEntity() {
        // Arrange
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("saveEntity");
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ArgumentCaptor<LogCorrecao> logCaptor = ArgumentCaptor.forClass(LogCorrecao.class);

        // Act
        auditAspect.logAfterWrite(joinPoint, null);

        // Assert
        verify(logRepository, times(1)).save(logCaptor.capture());
        LogCorrecao savedLog = logCaptor.getValue();

        assertThat(savedLog.getEntidadeTipo()).isEqualTo("Unknown");
        assertThat(savedLog.getValorNovo()).isNull();
    }

    @Test
    void logAfterWrite_WithCustomEntity_ShouldLogEntitySimpleName() {
        // Arrange
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("saveDizimo");
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario usuarioResult = new Usuario();
        ArgumentCaptor<LogCorrecao> logCaptor = ArgumentCaptor.forClass(LogCorrecao.class);

        // Act
        auditAspect.logAfterWrite(joinPoint, usuarioResult);

        // Assert
        verify(logRepository, times(1)).save(logCaptor.capture());
        LogCorrecao savedLog = logCaptor.getValue();

        assertThat(savedLog.getEntidadeTipo()).isEqualTo("Usuario");
        assertThat(savedLog.getValorNovo()).isNotNull();
    }

    @Test
    void logAfterWrite_WithNonUserDetailsPrincipal_ShouldLogWithNullUserId() {
        // Arrange
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("saveEntity");
        when(authentication.getPrincipal()).thenReturn("StringPrincipal");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Object result = new Object();
        ArgumentCaptor<LogCorrecao> logCaptor = ArgumentCaptor.forClass(LogCorrecao.class);

        // Act
        auditAspect.logAfterWrite(joinPoint, result);

        // Assert
        verify(logRepository, times(1)).save(logCaptor.capture());
        LogCorrecao savedLog = logCaptor.getValue();

        assertThat(savedLog.getUsuarioId()).isNull();
    }

    @Test
    void logAfterWrite_ShouldSetValorNovoToResultToString() {
        // Arrange
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("saveTest");

        String result = "TestResult";
        ArgumentCaptor<LogCorrecao> logCaptor = ArgumentCaptor.forClass(LogCorrecao.class);

        // Act
        auditAspect.logAfterWrite(joinPoint, result);

        // Assert
        verify(logRepository, times(1)).save(logCaptor.capture());
        LogCorrecao savedLog = logCaptor.getValue();

        assertThat(savedLog.getValorNovo()).isEqualTo("TestResult");
    }

    @Test
    void logAfterWrite_WithSaveAllMethod_ShouldLogAsInsertUpdate() {
        // Arrange
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("saveAllUsuarios");
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Object result = new Object();
        ArgumentCaptor<LogCorrecao> logCaptor = ArgumentCaptor.forClass(LogCorrecao.class);

        // Act
        auditAspect.logAfterWrite(joinPoint, result);

        // Assert
        verify(logRepository, times(1)).save(logCaptor.capture());
        LogCorrecao savedLog = logCaptor.getValue();

        assertThat(savedLog.getAcao()).isEqualTo("INSERT/UPDATE");
    }
}
