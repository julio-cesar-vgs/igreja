package br.com.igreja.ipiranga.modules.identity.application;

import br.com.igreja.ipiranga.infrastructure.security.JwtService;
import br.com.igreja.ipiranga.modules.identity.application.dto.AuthResponse;
import br.com.igreja.ipiranga.modules.identity.application.dto.LoginRequest;
import br.com.igreja.ipiranga.modules.identity.application.dto.RegisterRequest;
import br.com.igreja.ipiranga.modules.identity.domain.model.Usuario;
import br.com.igreja.ipiranga.modules.identity.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;

    @Captor
    private ArgumentCaptor<Map<String, Object>> mapCaptor;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequest.builder()
                .nome("João Silva")
                .email("joao@example.com")
                .senha("senha123")
                .igrejaId(1L)
                .role(Usuario.Role.ROLE_USER)
                .build();

        loginRequest = LoginRequest.builder()
                .email("joao@example.com")
                .password("senha123")
                .build();

        usuario = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@example.com")
                .senha("encodedPassword")
                .role(Usuario.Role.ROLE_USER)
                .igrejaId(1L)
                .build();
    }

    @Test
    void testRegister_Success() {
        // Given
        when(repository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getSenha())).thenReturn("encodedPassword");
        when(repository.save(any(Usuario.class))).thenReturn(usuario);
        when(jwtService.generateToken(anyMap(), any(Usuario.class))).thenReturn("jwt-token");

        // When
        AuthResponse response = authenticationService.register(registerRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwt-token");

        verify(repository).findByEmail(registerRequest.getEmail());
        verify(passwordEncoder).encode(registerRequest.getSenha());
        verify(repository).save(usuarioCaptor.capture());
        verify(jwtService).generateToken(mapCaptor.capture(), any(Usuario.class));

        Usuario savedUsuario = usuarioCaptor.getValue();
        assertThat(savedUsuario.getNome()).isEqualTo(registerRequest.getNome());
        assertThat(savedUsuario.getEmail()).isEqualTo(registerRequest.getEmail());
        assertThat(savedUsuario.getSenha()).isEqualTo("encodedPassword");
        assertThat(savedUsuario.getRole()).isEqualTo(registerRequest.getRole());
        assertThat(savedUsuario.getIgrejaId()).isEqualTo(registerRequest.getIgrejaId());

        Map<String, Object> extraClaims = mapCaptor.getValue();
        assertThat(extraClaims).containsEntry("igreja_id", registerRequest.getIgrejaId());
        assertThat(extraClaims).containsEntry("role", registerRequest.getRole().name());
    }

    @Test
    void testRegister_EmailAlreadyExists() {
        // Given
        when(repository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(usuario));

        // When & Then
        assertThatThrownBy(() -> authenticationService.register(registerRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Email já cadastrado");

        verify(repository).findByEmail(registerRequest.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(repository, never()).save(any(Usuario.class));
        verify(jwtService, never()).generateToken(anyMap(), any(Usuario.class));
    }

    @Test
    void testRegister_WithRoleAdmin() {
        // Given
        registerRequest.setRole(Usuario.Role.ROLE_ADMIN);
        when(repository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getSenha())).thenReturn("encodedPassword");
        when(repository.save(any(Usuario.class))).thenReturn(usuario);
        when(jwtService.generateToken(anyMap(), any(Usuario.class))).thenReturn("jwt-token");

        // When
        AuthResponse response = authenticationService.register(registerRequest);

        // Then
        assertThat(response).isNotNull();
        verify(repository).save(usuarioCaptor.capture());

        Usuario savedUsuario = usuarioCaptor.getValue();
        assertThat(savedUsuario.getRole()).isEqualTo(Usuario.Role.ROLE_ADMIN);
    }

    @Test
    void testRegister_WithRoleTesoureiro() {
        // Given
        registerRequest.setRole(Usuario.Role.ROLE_TESOUREIRO);
        when(repository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getSenha())).thenReturn("encodedPassword");
        when(repository.save(any(Usuario.class))).thenReturn(usuario);
        when(jwtService.generateToken(anyMap(), any(Usuario.class))).thenReturn("jwt-token");

        // When
        AuthResponse response = authenticationService.register(registerRequest);

        // Then
        assertThat(response).isNotNull();
        verify(repository).save(usuarioCaptor.capture());

        Usuario savedUsuario = usuarioCaptor.getValue();
        assertThat(savedUsuario.getRole()).isEqualTo(Usuario.Role.ROLE_TESOUREIRO);
    }

    @Test
    void testAuthenticate_Success() {
        // Given
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(repository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(usuario));
        when(jwtService.generateToken(anyMap(), any(Usuario.class))).thenReturn("jwt-token");

        // When
        AuthResponse response = authenticationService.authenticate(loginRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwt-token");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(repository).findByEmail(loginRequest.getEmail());
        verify(jwtService).generateToken(mapCaptor.capture(), eq(usuario));

        Map<String, Object> extraClaims = mapCaptor.getValue();
        assertThat(extraClaims).containsEntry("igreja_id", usuario.getIgrejaId());
        assertThat(extraClaims).containsEntry("role", usuario.getRole().name());
    }

    @Test
    void testAuthenticate_UserNotFound() {
        // Given
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(repository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> authenticationService.authenticate(loginRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Usuário não encontrado");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(repository).findByEmail(loginRequest.getEmail());
        verify(jwtService, never()).generateToken(anyMap(), any(Usuario.class));
    }

    @Test
    void testAuthenticate_AuthenticationFails() {
        // Given
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Bad credentials"));

        // When & Then
        assertThatThrownBy(() -> authenticationService.authenticate(loginRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Bad credentials");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(repository, never()).findByEmail(anyString());
        verify(jwtService, never()).generateToken(anyMap(), any(Usuario.class));
    }

    @Test
    void testRegister_SetsIgrejaIdCorrectly() {
        // Given
        Long expectedIgrejaId = 5L;
        registerRequest.setIgrejaId(expectedIgrejaId);
        when(repository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getSenha())).thenReturn("encodedPassword");
        when(repository.save(any(Usuario.class))).thenReturn(usuario);
        when(jwtService.generateToken(anyMap(), any(Usuario.class))).thenReturn("jwt-token");

        // When
        authenticationService.register(registerRequest);

        // Then
        verify(repository).save(usuarioCaptor.capture());
        Usuario savedUsuario = usuarioCaptor.getValue();
        assertThat(savedUsuario.getIgrejaId()).isEqualTo(expectedIgrejaId);
    }

    @Test
    void testAuthenticate_GeneratesTokenWithCorrectClaims() {
        // Given
        Usuario usuarioWithClaims = Usuario.builder()
                .id(1L)
                .nome("Test User")
                .email("test@example.com")
                .senha("password")
                .role(Usuario.Role.ROLE_ADMIN)
                .igrejaId(10L)
                .build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(repository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(usuarioWithClaims));
        when(jwtService.generateToken(anyMap(), any(Usuario.class))).thenReturn("jwt-token");

        // When
        authenticationService.authenticate(loginRequest);

        // Then
        verify(jwtService).generateToken(mapCaptor.capture(), eq(usuarioWithClaims));

        Map<String, Object> extraClaims = mapCaptor.getValue();
        assertThat(extraClaims).hasSize(2);
        assertThat(extraClaims.get("igreja_id")).isEqualTo(10L);
        assertThat(extraClaims.get("role")).isEqualTo("ROLE_ADMIN");
    }
}
