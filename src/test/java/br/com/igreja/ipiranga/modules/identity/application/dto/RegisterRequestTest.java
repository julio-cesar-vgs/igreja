package br.com.igreja.ipiranga.modules.identity.application.dto;

import br.com.igreja.ipiranga.modules.identity.domain.model.Usuario;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testNoArgsConstructor() {
        // When
        RegisterRequest request = new RegisterRequest();

        // Then
        assertThat(request).isNotNull();
        assertThat(request.getNome()).isNull();
        assertThat(request.getEmail()).isNull();
        assertThat(request.getSenha()).isNull();
        assertThat(request.getIgrejaId()).isNull();
        assertThat(request.getRole()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        String nome = "João Silva";
        String email = "joao@example.com";
        String senha = "senha123";
        Long igrejaId = 1L;
        Usuario.Role role = Usuario.Role.ROLE_USER;

        // When
        RegisterRequest request = new RegisterRequest(nome, email, senha, igrejaId, role);

        // Then
        assertThat(request).isNotNull();
        assertThat(request.getNome()).isEqualTo(nome);
        assertThat(request.getEmail()).isEqualTo(email);
        assertThat(request.getSenha()).isEqualTo(senha);
        assertThat(request.getIgrejaId()).isEqualTo(igrejaId);
        assertThat(request.getRole()).isEqualTo(role);
    }

    @Test
    void testBuilder() {
        // Given
        String nome = "Maria Santos";
        String email = "maria@example.com";
        String senha = "password123";
        Long igrejaId = 5L;
        Usuario.Role role = Usuario.Role.ROLE_ADMIN;

        // When
        RegisterRequest request = RegisterRequest.builder()
                .nome(nome)
                .email(email)
                .senha(senha)
                .igrejaId(igrejaId)
                .role(role)
                .build();

        // Then
        assertThat(request).isNotNull();
        assertThat(request.getNome()).isEqualTo(nome);
        assertThat(request.getEmail()).isEqualTo(email);
        assertThat(request.getSenha()).isEqualTo(senha);
        assertThat(request.getIgrejaId()).isEqualTo(igrejaId);
        assertThat(request.getRole()).isEqualTo(role);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        RegisterRequest request = new RegisterRequest();
        String nome = "Pedro Costa";
        String email = "pedro@example.com";
        String senha = "securePass";
        Long igrejaId = 10L;
        Usuario.Role role = Usuario.Role.ROLE_TESOUREIRO;

        // When
        request.setNome(nome);
        request.setEmail(email);
        request.setSenha(senha);
        request.setIgrejaId(igrejaId);
        request.setRole(role);

        // Then
        assertThat(request.getNome()).isEqualTo(nome);
        assertThat(request.getEmail()).isEqualTo(email);
        assertThat(request.getSenha()).isEqualTo(senha);
        assertThat(request.getIgrejaId()).isEqualTo(igrejaId);
        assertThat(request.getRole()).isEqualTo(role);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        RegisterRequest request1 = RegisterRequest.builder()
                .nome("João Silva")
                .email("joao@example.com")
                .senha("senha123")
                .igrejaId(1L)
                .role(Usuario.Role.ROLE_USER)
                .build();

        RegisterRequest request2 = RegisterRequest.builder()
                .nome("João Silva")
                .email("joao@example.com")
                .senha("senha123")
                .igrejaId(1L)
                .role(Usuario.Role.ROLE_USER)
                .build();

        RegisterRequest request3 = RegisterRequest.builder()
                .nome("Maria Santos")
                .email("maria@example.com")
                .senha("password123")
                .igrejaId(2L)
                .role(Usuario.Role.ROLE_ADMIN)
                .build();

        // Then
        assertThat(request1).isEqualTo(request2);
        assertThat(request1).hasSameHashCodeAs(request2);
        assertThat(request1).isNotEqualTo(request3);
        assertThat(request1.hashCode()).isNotEqualTo(request3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .nome("João Silva")
                .email("joao@example.com")
                .senha("senha123")
                .igrejaId(1L)
                .role(Usuario.Role.ROLE_USER)
                .build();

        // When
        String toString = request.toString();

        // Then
        assertThat(toString).contains("RegisterRequest");
        assertThat(toString).contains("nome=João Silva");
        assertThat(toString).contains("email=joao@example.com");
        assertThat(toString).contains("role=ROLE_USER");
    }

    @Test
    void testValidationWithValidData() {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .nome("João Silva")
                .email("joao@example.com")
                .senha("senha123")
                .igrejaId(1L)
                .role(Usuario.Role.ROLE_USER)
                .build();

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void testValidationWithBlankNome() {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .nome("")
                .email("joao@example.com")
                .senha("senha123")
                .igrejaId(1L)
                .role(Usuario.Role.ROLE_USER)
                .build();

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O nome é obrigatório");
    }

    @Test
    void testValidationWithNullNome() {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .nome(null)
                .email("joao@example.com")
                .senha("senha123")
                .igrejaId(1L)
                .role(Usuario.Role.ROLE_USER)
                .build();

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O nome é obrigatório");
    }

    @Test
    void testValidationWithBlankEmail() {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .nome("João Silva")
                .email("")
                .senha("senha123")
                .igrejaId(1L)
                .role(Usuario.Role.ROLE_USER)
                .build();

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    void testValidationWithInvalidEmail() {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .nome("João Silva")
                .email("invalid-email")
                .senha("senha123")
                .igrejaId(1L)
                .role(Usuario.Role.ROLE_USER)
                .build();

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Email inválido");
    }

    @Test
    void testValidationWithBlankSenha() {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .nome("João Silva")
                .email("joao@example.com")
                .senha("")
                .igrejaId(1L)
                .role(Usuario.Role.ROLE_USER)
                .build();

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("A senha é obrigatória");
    }

    @Test
    void testValidationWithNullIgrejaId() {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .nome("João Silva")
                .email("joao@example.com")
                .senha("senha123")
                .igrejaId(null)
                .role(Usuario.Role.ROLE_USER)
                .build();

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O ID da igreja é obrigatório");
    }

    @Test
    void testValidationWithNullRole() {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .nome("João Silva")
                .email("joao@example.com")
                .senha("senha123")
                .igrejaId(1L)
                .role(null)
                .build();

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O perfil (role) é obrigatório");
    }

    @Test
    void testBuilderWithAllRoles() {
        // Test ROLE_USER
        RegisterRequest request1 = RegisterRequest.builder()
                .nome("User")
                .email("user@example.com")
                .senha("senha")
                .igrejaId(1L)
                .role(Usuario.Role.ROLE_USER)
                .build();
        assertThat(request1.getRole()).isEqualTo(Usuario.Role.ROLE_USER);

        // Test ROLE_ADMIN
        RegisterRequest request2 = RegisterRequest.builder()
                .nome("Admin")
                .email("admin@example.com")
                .senha("senha")
                .igrejaId(1L)
                .role(Usuario.Role.ROLE_ADMIN)
                .build();
        assertThat(request2.getRole()).isEqualTo(Usuario.Role.ROLE_ADMIN);

        // Test ROLE_TESOUREIRO
        RegisterRequest request3 = RegisterRequest.builder()
                .nome("Tesoureiro")
                .email("tesoureiro@example.com")
                .senha("senha")
                .igrejaId(1L)
                .role(Usuario.Role.ROLE_TESOUREIRO)
                .build();
        assertThat(request3.getRole()).isEqualTo(Usuario.Role.ROLE_TESOUREIRO);
    }
}
