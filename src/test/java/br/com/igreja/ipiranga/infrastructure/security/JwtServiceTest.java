package br.com.igreja.ipiranga.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class JwtServiceTest {

    private JwtService jwtService;
    private String secretKey;
    private long jwtExpiration;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
        jwtExpiration = 86400000; // 24 hours

        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", jwtExpiration);

        userDetails = User.builder()
                .username("test@example.com")
                .password("password")
                .authorities(Collections.emptyList())
                .build();
    }

    @Test
    void generateToken_ShouldCreateValidToken() {
        // Arrange
        Long igrejaId = 1L;

        // Act
        String token = jwtService.generateToken(userDetails, igrejaId);

        // Assert
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        // Arrange
        Long igrejaId = 1L;
        String token = jwtService.generateToken(userDetails, igrejaId);

        // Act
        String extractedUsername = jwtService.extractUsername(token);

        // Assert
        assertThat(extractedUsername).isEqualTo("test@example.com");
    }

    @Test
    void extractIgrejaId_ShouldReturnCorrectIgrejaId() {
        // Arrange
        Long igrejaId = 42L;
        String token = jwtService.generateToken(userDetails, igrejaId);

        // Act
        Long extractedIgrejaId = jwtService.extractIgrejaId(token);

        // Assert
        assertThat(extractedIgrejaId).isEqualTo(42L);
    }

    @Test
    void isTokenValid_WithValidToken_ShouldReturnTrue() {
        // Arrange
        Long igrejaId = 1L;
        String token = jwtService.generateToken(userDetails, igrejaId);

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    void isTokenValid_WithWrongUsername_ShouldReturnFalse() {
        // Arrange
        Long igrejaId = 1L;
        String token = jwtService.generateToken(userDetails, igrejaId);

        UserDetails wrongUserDetails = User.builder()
                .username("wrong@example.com")
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        // Act
        boolean isValid = jwtService.isTokenValid(token, wrongUserDetails);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    void isTokenValid_WithExpiredToken_ShouldReturnFalse() {
        // Arrange
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", -1000L);
        Long igrejaId = 1L;
        String expiredToken = jwtService.generateToken(userDetails, igrejaId);

        // Reset expiration to normal
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", jwtExpiration);

        // Act
        boolean isValid = jwtService.isTokenValid(expiredToken, userDetails);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    void generateToken_WithExtraClaims_ShouldIncludeAllClaims() {
        // Arrange
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("igreja_id", 5L);
        extraClaims.put("custom_claim", "custom_value");

        // Act
        String token = jwtService.generateToken(extraClaims, userDetails);

        // Assert
        assertThat(token).isNotNull();
        Long igrejaId = jwtService.extractIgrejaId(token);
        assertThat(igrejaId).isEqualTo(5L);

        String customClaim = jwtService.extractClaim(token, claims -> claims.get("custom_claim", String.class));
        assertThat(customClaim).isEqualTo("custom_value");
    }

    @Test
    void extractClaim_ShouldExtractSpecificClaim() {
        // Arrange
        Long igrejaId = 1L;
        String token = jwtService.generateToken(userDetails, igrejaId);

        // Act
        String subject = jwtService.extractClaim(token, Claims::getSubject);
        Date expiration = jwtService.extractClaim(token, Claims::getExpiration);
        Date issuedAt = jwtService.extractClaim(token, Claims::getIssuedAt);

        // Assert
        assertThat(subject).isEqualTo("test@example.com");
        assertThat(expiration).isNotNull();
        assertThat(issuedAt).isNotNull();
        assertThat(expiration).isAfter(issuedAt);
    }

    @Test
    void generateToken_ShouldSetCorrectExpiration() {
        // Arrange
        Long igrejaId = 1L;

        // Act
        String token = jwtService.generateToken(userDetails, igrejaId);
        Date expiration = jwtService.extractClaim(token, Claims::getExpiration);
        Date issuedAt = jwtService.extractClaim(token, Claims::getIssuedAt);

        // Assert
        long expectedExpiration = issuedAt.getTime() + jwtExpiration;
        assertThat(expiration.getTime()).isCloseTo(expectedExpiration, within(1000L));
    }

    @Test
    void generateToken_ShouldSetIssuedAtToCurrentTime() {
        // Arrange
        Long igrejaId = 1L;
        long beforeGeneration = System.currentTimeMillis();

        // Act
        String token = jwtService.generateToken(userDetails, igrejaId);
        Date issuedAt = jwtService.extractClaim(token, Claims::getIssuedAt);

        // Assert
        long afterGeneration = System.currentTimeMillis();
        assertThat(issuedAt.getTime()).isBetween(beforeGeneration - 1000, afterGeneration + 1000);
    }

    @Test
    void extractUsername_WithMultipleTokens_ShouldReturnCorrectUsername() {
        // Arrange
        UserDetails user1 = User.builder()
                .username("user1@example.com")
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        UserDetails user2 = User.builder()
                .username("user2@example.com")
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        String token1 = jwtService.generateToken(user1, 1L);
        String token2 = jwtService.generateToken(user2, 2L);

        // Act & Assert
        assertThat(jwtService.extractUsername(token1)).isEqualTo("user1@example.com");
        assertThat(jwtService.extractUsername(token2)).isEqualTo("user2@example.com");
        assertThat(jwtService.extractIgrejaId(token1)).isEqualTo(1L);
        assertThat(jwtService.extractIgrejaId(token2)).isEqualTo(2L);
    }
}
