package br.com.igreja.ipiranga.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Serviço de utilidade para manipulação de JSON Web Tokens (JWT).
 * <p>
 * Esta classe é responsável por todo o ciclo de vida de um token JWT dentro da aplicação:
 * <ul>
 *     <li>Geração de tokens assinados (criação).</li>
 *     <li>Validação de integridade e expiração (verificação).</li>
 *     <li>Extração de informações (claims) contidas no payload (leitura).</li>
 * </ul>
 * </p>
 * <p>
 * <strong>Algoritmo de Assinatura:</strong> HMAC SHA-256 (HS256).
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 * @see <a href="https://jwt.io/">JSON Web Token Introduction</a>
 */
@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    /**
     * Extrai o nome de usuário (subject) do token JWT.
     *
     * @param token O token JWT (string completa).
     * @return O username (email) contido no subject do token.
     * @throws io.jsonwebtoken.JwtException se o token for inválido.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrai o ID da Igreja (Tenant) armazenado nas claims personalizadas do token.
     * <p>
     * Este ID é fundamental para garantir o isolamento dos dados (multi-tenancy) em cada requisição.
     * </p>
     *
     * @param token O token JWT.
     * @return O Long representando o ID da igreja.
     */
    public Long extractIgrejaId(String token) {
        return extractClaim(token, claims -> claims.get("igreja_id", Long.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Gera um token JWT com claims extras, além das padrão.
     * <p>
     * Adiciona automaticamente o {@code igrejaId} fornecido ao payload do token.
     * </p>
     *
     * @param userDetails Os detalhes do usuário autenticado.
     * @param igrejaId O ID da igreja vinculada ao usuário.
     * @return Uma string contendo o token JWT assinado.
     */
    public String generateToken(UserDetails userDetails, Long igrejaId) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("igreja_id", igrejaId);
        return generateToken(extraClaims, userDetails);
    }


    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Valida se um token JWT é legítimo e pertence ao usuário fornecido.
     * <p>
     * A validação inclui:
     * <ul>
     *     <li>Verificação da assinatura digital.</li>
     *     <li>Verificação se o token não está expirado.</li>
     *     <li>Conferência se o username do token corresponde ao UserDetails.</li>
     * </ul>
     * </p>
     *
     * @param token O token JWT a ser validado.
     * @param userDetails Os detalhes do usuário para comparação.
     * @return {@code true} se o token for válido e pertencer ao usuário; {@code false} caso contrário.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
