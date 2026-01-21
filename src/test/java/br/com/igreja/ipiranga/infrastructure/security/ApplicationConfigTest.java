package br.com.igreja.ipiranga.infrastructure.security;

import br.com.igreja.ipiranga.modules.identity.domain.model.Usuario;
import br.com.igreja.ipiranga.modules.identity.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationConfigTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @InjectMocks
    private ApplicationConfig applicationConfig;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setSenha("password123");
    }

    @Test
    void userDetailsService_ShouldReturnCustomUserDetails_WhenUserExists() {
        // Arrange
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.of(usuario));
        UserDetailsService userDetailsService = applicationConfig.userDetailsService();

        // Act
        var userDetails = userDetailsService.loadUserByUsername("test@example.com");

        // Assert
        assertThat(userDetails).isNotNull();
        assertThat(userDetails).isInstanceOf(CustomUserDetails.class);
        assertThat(userDetails.getUsername()).isEqualTo("test@example.com");
        verify(usuarioRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void userDetailsService_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(usuarioRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        UserDetailsService userDetailsService = applicationConfig.userDetailsService();

        // Act & Assert
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("nonexistent@example.com"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Usuário não encontrado");
        verify(usuarioRepository, times(1)).findByEmail("nonexistent@example.com");
    }

    @Test
    void authenticationProvider_ShouldReturnDaoAuthenticationProvider() {
        // Act
        AuthenticationProvider authProvider = applicationConfig.authenticationProvider();

        // Assert
        assertThat(authProvider).isNotNull();
        assertThat(authProvider).isInstanceOf(DaoAuthenticationProvider.class);
    }

    @Test
    void authenticationManager_ShouldReturnAuthenticationManager() throws Exception {
        // Arrange
        AuthenticationManager mockAuthManager = mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(mockAuthManager);

        // Act
        AuthenticationManager authManager = applicationConfig.authenticationManager(authenticationConfiguration);

        // Assert
        assertThat(authManager).isNotNull();
        assertThat(authManager).isEqualTo(mockAuthManager);
        verify(authenticationConfiguration, times(1)).getAuthenticationManager();
    }

    @Test
    void passwordEncoder_ShouldReturnBCryptPasswordEncoder() {
        // Act
        PasswordEncoder passwordEncoder = applicationConfig.passwordEncoder();

        // Assert
        assertThat(passwordEncoder).isNotNull();
        assertThat(passwordEncoder).isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    void passwordEncoder_ShouldEncodePassword() {
        // Arrange
        PasswordEncoder passwordEncoder = applicationConfig.passwordEncoder();
        String rawPassword = "myPassword123";

        // Act
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Assert
        assertThat(encodedPassword).isNotNull();
        assertThat(encodedPassword).isNotEqualTo(rawPassword);
        assertThat(passwordEncoder.matches(rawPassword, encodedPassword)).isTrue();
    }
}
