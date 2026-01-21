package br.com.igreja.ipiranga.modules.identity.application.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginRequestTest {

    @Test
    void testNoArgsConstructor() {
        // When
        LoginRequest request = new LoginRequest();

        // Then
        assertThat(request).isNotNull();
        assertThat(request.getEmail()).isNull();
        assertThat(request.getPassword()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        String email = "test@example.com";
        String password = "password123";

        // When
        LoginRequest request = new LoginRequest(email, password);

        // Then
        assertThat(request).isNotNull();
        assertThat(request.getEmail()).isEqualTo(email);
        assertThat(request.getPassword()).isEqualTo(password);
    }

    @Test
    void testBuilder() {
        // Given
        String email = "user@domain.com";
        String password = "securePassword";

        // When
        LoginRequest request = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        // Then
        assertThat(request).isNotNull();
        assertThat(request.getEmail()).isEqualTo(email);
        assertThat(request.getPassword()).isEqualTo(password);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        LoginRequest request = new LoginRequest();
        String email = "admin@church.com";
        String password = "adminPass123";

        // When
        request.setEmail(email);
        request.setPassword(password);

        // Then
        assertThat(request.getEmail()).isEqualTo(email);
        assertThat(request.getPassword()).isEqualTo(password);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        LoginRequest request1 = LoginRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        LoginRequest request2 = LoginRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        LoginRequest request3 = LoginRequest.builder()
                .email("different@example.com")
                .password("differentPassword")
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
        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        // When
        String toString = request.toString();

        // Then
        assertThat(toString).contains("LoginRequest");
        assertThat(toString).contains("email=test@example.com");
        assertThat(toString).contains("password=password123");
    }

    @Test
    void testBuilderWithNullValues() {
        // When
        LoginRequest request = LoginRequest.builder()
                .email(null)
                .password(null)
                .build();

        // Then
        assertThat(request.getEmail()).isNull();
        assertThat(request.getPassword()).isNull();
    }

    @Test
    void testBuilderWithEmptyValues() {
        // When
        LoginRequest request = LoginRequest.builder()
                .email("")
                .password("")
                .build();

        // Then
        assertThat(request.getEmail()).isEmpty();
        assertThat(request.getPassword()).isEmpty();
    }
}
