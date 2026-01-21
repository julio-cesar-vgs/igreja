package br.com.igreja.ipiranga.modules.identity.application.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthResponseTest {

    @Test
    void testNoArgsConstructor() {
        // When
        AuthResponse response = new AuthResponse();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.test";

        // When
        AuthResponse response = new AuthResponse(token);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo(token);
    }

    @Test
    void testBuilder() {
        // Given
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.test.token";

        // When
        AuthResponse response = AuthResponse.builder()
                .token(token)
                .build();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo(token);
    }

    @Test
    void testSetterAndGetter() {
        // Given
        AuthResponse response = new AuthResponse();
        String token = "bearer.jwt.token";

        // When
        response.setToken(token);

        // Then
        assertThat(response.getToken()).isEqualTo(token);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        String token = "same.token.value";
        AuthResponse response1 = AuthResponse.builder()
                .token(token)
                .build();

        AuthResponse response2 = AuthResponse.builder()
                .token(token)
                .build();

        AuthResponse response3 = AuthResponse.builder()
                .token("different.token.value")
                .build();

        // Then
        assertThat(response1).isEqualTo(response2);
        assertThat(response1).hasSameHashCodeAs(response2);
        assertThat(response1).isNotEqualTo(response3);
        assertThat(response1.hashCode()).isNotEqualTo(response3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        String token = "test.token.value";
        AuthResponse response = AuthResponse.builder()
                .token(token)
                .build();

        // When
        String toString = response.toString();

        // Then
        assertThat(toString).contains("AuthResponse");
        assertThat(toString).contains("token=test.token.value");
    }

    @Test
    void testBuilderWithNullToken() {
        // When
        AuthResponse response = AuthResponse.builder()
                .token(null)
                .build();

        // Then
        assertThat(response.getToken()).isNull();
    }

    @Test
    void testBuilderWithEmptyToken() {
        // When
        AuthResponse response = AuthResponse.builder()
                .token("")
                .build();

        // Then
        assertThat(response.getToken()).isEmpty();
    }
}
