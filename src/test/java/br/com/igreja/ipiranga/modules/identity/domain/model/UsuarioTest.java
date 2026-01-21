package br.com.igreja.ipiranga.modules.identity.domain.model;

import br.com.igreja.ipiranga.modules.igreja.domain.model.Igreja;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class UsuarioTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Usuario usuario = new Usuario();

        // Then
        assertThat(usuario).isNotNull();
        assertThat(usuario.getId()).isNull();
        assertThat(usuario.getEmail()).isNull();
        assertThat(usuario.getSenha()).isNull();
        assertThat(usuario.getNome()).isNull();
        assertThat(usuario.getIgreja()).isNull();
        assertThat(usuario.getRole()).isNull();
        assertThat(usuario.getIgrejaId()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        String email = "test@example.com";
        String senha = "encodedPassword";
        String nome = "João Silva";
        Igreja igreja = Igreja.builder().id(10L).build();
        Usuario.Role role = Usuario.Role.ROLE_USER;

        // When
        Usuario usuario = new Usuario(id, email, senha, nome, igreja, role);

        // Then
        assertThat(usuario).isNotNull();
        assertThat(usuario.getId()).isEqualTo(id);
        assertThat(usuario.getEmail()).isEqualTo(email);
        assertThat(usuario.getSenha()).isEqualTo(senha);
        assertThat(usuario.getNome()).isEqualTo(nome);
        assertThat(usuario.getIgreja()).isEqualTo(igreja);
        assertThat(usuario.getRole()).isEqualTo(role);
    }

    @Test
    void testBuilder() {
        // Given
        Long id = 1L;
        String email = "user@domain.com";
        String senha = "securePassword";
        String nome = "Maria Santos";
        Igreja igreja = Igreja.builder().id(5L).build();
        Usuario.Role role = Usuario.Role.ROLE_ADMIN;
        Long igrejaId = 5L;

        // When
        Usuario usuario = Usuario.builder()
                .id(id)
                .email(email)
                .senha(senha)
                .nome(nome)
                .igreja(igreja)
                .role(role)
                .igrejaId(igrejaId)
                .build();

        // Then
        assertThat(usuario).isNotNull();
        assertThat(usuario.getId()).isEqualTo(id);
        assertThat(usuario.getEmail()).isEqualTo(email);
        assertThat(usuario.getSenha()).isEqualTo(senha);
        assertThat(usuario.getNome()).isEqualTo(nome);
        assertThat(usuario.getIgreja()).isEqualTo(igreja);
        assertThat(usuario.getRole()).isEqualTo(role);
        assertThat(usuario.getIgrejaId()).isEqualTo(igrejaId);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Usuario usuario = new Usuario();
        Long id = 2L;
        String email = "admin@church.com";
        String senha = "adminPass";
        String nome = "Pedro Costa";
        Igreja igreja = Igreja.builder().id(15L).build();
        Usuario.Role role = Usuario.Role.ROLE_TESOUREIRO;
        Long igrejaId = 15L;

        // When
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setNome(nome);
        usuario.setIgreja(igreja);
        usuario.setRole(role);
        usuario.setIgrejaId(igrejaId);

        // Then
        assertThat(usuario.getId()).isEqualTo(id);
        assertThat(usuario.getEmail()).isEqualTo(email);
        assertThat(usuario.getSenha()).isEqualTo(senha);
        assertThat(usuario.getNome()).isEqualTo(nome);
        assertThat(usuario.getIgreja()).isEqualTo(igreja);
        assertThat(usuario.getRole()).isEqualTo(role);
        assertThat(usuario.getIgrejaId()).isEqualTo(igrejaId);
    }

    @Test
    void testGetAuthoritiesWithRoleUser() {
        // Given
        Usuario usuario = Usuario.builder()
                .email("user@example.com")
                .senha("password")
                .nome("User")
                .role(Usuario.Role.ROLE_USER)
                .build();

        // When
        Collection<? extends GrantedAuthority> authorities = usuario.getAuthorities();

        // Then
        assertThat(authorities).hasSize(1);
        List<String> authorityStrings = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        assertThat(authorityStrings).containsExactly("ROLE_USER");
    }

    @Test
    void testGetAuthoritiesWithRoleAdmin() {
        // Given
        Usuario usuario = Usuario.builder()
                .email("admin@example.com")
                .senha("password")
                .nome("Admin")
                .role(Usuario.Role.ROLE_ADMIN)
                .build();

        // When
        Collection<? extends GrantedAuthority> authorities = usuario.getAuthorities();

        // Then
        assertThat(authorities).hasSize(1);
        List<String> authorityStrings = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        assertThat(authorityStrings).containsExactly("ROLE_ADMIN");
    }

    @Test
    void testGetAuthoritiesWithRoleTesoureiro() {
        // Given
        Usuario usuario = Usuario.builder()
                .email("tesoureiro@example.com")
                .senha("password")
                .nome("Tesoureiro")
                .role(Usuario.Role.ROLE_TESOUREIRO)
                .build();

        // When
        Collection<? extends GrantedAuthority> authorities = usuario.getAuthorities();

        // Then
        assertThat(authorities).hasSize(1);
        List<String> authorityStrings = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        assertThat(authorityStrings).containsExactly("ROLE_TESOUREIRO");
    }

    @Test
    void testGetPassword() {
        // Given
        String senha = "encodedPassword123";
        Usuario usuario = Usuario.builder()
                .email("test@example.com")
                .senha(senha)
                .nome("Test User")
                .role(Usuario.Role.ROLE_USER)
                .build();

        // When
        String password = usuario.getPassword();

        // Then
        assertThat(password).isEqualTo(senha);
    }

    @Test
    void testGetUsername() {
        // Given
        String email = "user@domain.com";
        Usuario usuario = Usuario.builder()
                .email(email)
                .senha("password")
                .nome("User")
                .role(Usuario.Role.ROLE_USER)
                .build();

        // When
        String username = usuario.getUsername();

        // Then
        assertThat(username).isEqualTo(email);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Igreja igreja = Igreja.builder().id(5L).build();
        Usuario usuario1 = Usuario.builder()
                .id(1L)
                .email("test@example.com")
                .senha("password")
                .nome("João Silva")
                .igreja(igreja)
                .role(Usuario.Role.ROLE_USER)
                .igrejaId(5L)
                .build();

        Usuario usuario2 = Usuario.builder()
                .id(1L)
                .email("test@example.com")
                .senha("password")
                .nome("João Silva")
                .igreja(igreja)
                .role(Usuario.Role.ROLE_USER)
                .igrejaId(5L)
                .build();

        Usuario usuario3 = Usuario.builder()
                .id(2L)
                .email("other@example.com")
                .senha("password")
                .nome("Maria Santos")
                .igreja(igreja)
                .role(Usuario.Role.ROLE_ADMIN)
                .igrejaId(5L)
                .build();

        // Then
        assertThat(usuario1).isEqualTo(usuario2);
        assertThat(usuario1).hasSameHashCodeAs(usuario2);
        assertThat(usuario1).isNotEqualTo(usuario3);
        assertThat(usuario1.hashCode()).isNotEqualTo(usuario3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        Usuario usuario = Usuario.builder()
                .id(1L)
                .email("test@example.com")
                .senha("password")
                .nome("João Silva")
                .role(Usuario.Role.ROLE_USER)
                .igrejaId(5L)
                .build();

        // When
        String toString = usuario.toString();

        // Then
        assertThat(toString).contains("Usuario");
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("email=test@example.com");
        assertThat(toString).contains("nome=João Silva");
        assertThat(toString).contains("role=ROLE_USER");
    }

    @Test
    void testRoleEnum() {
        // Test all enum values
        assertThat(Usuario.Role.values()).containsExactlyInAnyOrder(
                Usuario.Role.ROLE_ADMIN,
                Usuario.Role.ROLE_USER,
                Usuario.Role.ROLE_TESOUREIRO
        );

        // Test enum valueOf
        assertThat(Usuario.Role.valueOf("ROLE_ADMIN")).isEqualTo(Usuario.Role.ROLE_ADMIN);
        assertThat(Usuario.Role.valueOf("ROLE_USER")).isEqualTo(Usuario.Role.ROLE_USER);
        assertThat(Usuario.Role.valueOf("ROLE_TESOUREIRO")).isEqualTo(Usuario.Role.ROLE_TESOUREIRO);
    }

    @Test
    void testInheritedTenantEntityFields() {
        // Given
        Usuario usuario = new Usuario();
        Long igrejaId = 100L;

        // When
        usuario.setIgrejaId(igrejaId);

        // Then
        assertThat(usuario.getIgrejaId()).isEqualTo(igrejaId);
    }

    @Test
    void testUserDetailsAccountMethods() {
        // Given
        Usuario usuario = Usuario.builder()
                .email("test@example.com")
                .senha("password")
                .nome("Test User")
                .role(Usuario.Role.ROLE_USER)
                .build();

        // Then - UserDetails default methods (not overridden, so they return true by default)
        // These methods are inherited from UserDetails but not overridden in Usuario
        // Spring Security will use default behavior
        assertThat(usuario.getUsername()).isEqualTo("test@example.com");
        assertThat(usuario.getPassword()).isEqualTo("password");
        assertThat(usuario.getAuthorities()).isNotEmpty();
    }
}
