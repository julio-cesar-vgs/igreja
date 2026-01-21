package br.com.igreja.ipiranga.infrastructure.security;

import br.com.igreja.ipiranga.modules.identity.domain.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class CustomUserDetailsTest {

    private Usuario usuario;
    private CustomUserDetails customUserDetails;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .email("test@example.com")
                .senha("encodedPassword123")
                .nome("Test User")
                .role(Usuario.Role.ROLE_USER)
                .build();
        customUserDetails = new CustomUserDetails(usuario);
    }

    @Test
    void getAuthorities_ShouldReturnRoleBasedAuthority() {
        // Act
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();

        // Assert
        assertThat(authorities).isNotNull();
        assertThat(authorities).hasSize(1);
        assertThat(authorities.iterator().next().getAuthority()).isEqualTo("ROLE_USER");
    }

    @Test
    void getAuthorities_WithAdminRole_ShouldReturnAdminAuthority() {
        // Arrange
        usuario.setRole(Usuario.Role.ROLE_ADMIN);
        CustomUserDetails adminUserDetails = new CustomUserDetails(usuario);

        // Act
        Collection<? extends GrantedAuthority> authorities = adminUserDetails.getAuthorities();

        // Assert
        assertThat(authorities).isNotNull();
        assertThat(authorities).hasSize(1);
        assertThat(authorities.iterator().next().getAuthority()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void getAuthorities_WithTesoureiroRole_ShouldReturnTesoureiroAuthority() {
        // Arrange
        usuario.setRole(Usuario.Role.ROLE_TESOUREIRO);
        CustomUserDetails tesoureiroUserDetails = new CustomUserDetails(usuario);

        // Act
        Collection<? extends GrantedAuthority> authorities = tesoureiroUserDetails.getAuthorities();

        // Assert
        assertThat(authorities).isNotNull();
        assertThat(authorities).hasSize(1);
        assertThat(authorities.iterator().next().getAuthority()).isEqualTo("ROLE_TESOUREIRO");
    }

    @Test
    void getPassword_ShouldReturnUsuarioSenha() {
        // Act
        String password = customUserDetails.getPassword();

        // Assert
        assertThat(password).isEqualTo("encodedPassword123");
    }

    @Test
    void getUsername_ShouldReturnUsuarioEmail() {
        // Act
        String username = customUserDetails.getUsername();

        // Assert
        assertThat(username).isEqualTo("test@example.com");
    }

    @Test
    void isAccountNonExpired_ShouldReturnTrue() {
        // Act
        boolean result = customUserDetails.isAccountNonExpired();

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void isAccountNonLocked_ShouldReturnTrue() {
        // Act
        boolean result = customUserDetails.isAccountNonLocked();

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void isCredentialsNonExpired_ShouldReturnTrue() {
        // Act
        boolean result = customUserDetails.isCredentialsNonExpired();

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void isEnabled_ShouldReturnTrue() {
        // Act
        boolean result = customUserDetails.isEnabled();

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void getUsuario_ShouldReturnOriginalUsuario() {
        // Act
        Usuario retrievedUsuario = customUserDetails.getUsuario();

        // Assert
        assertThat(retrievedUsuario).isNotNull();
        assertThat(retrievedUsuario).isEqualTo(usuario);
        assertThat(retrievedUsuario.getId()).isEqualTo(1L);
        assertThat(retrievedUsuario.getEmail()).isEqualTo("test@example.com");
    }
}
