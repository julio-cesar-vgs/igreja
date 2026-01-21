package br.com.igreja.ipiranga.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe principal de configuração de segurança do Spring Security.
 * <p>
 * Define as regras de controle de acesso (Authorization), configuração de filtros e gerenciamento de sessão.
 * A aplicação segue uma arquitetura <strong>Stateless</strong> (sem sessão no servidor) baseada em JWT.
 * </p>
 * <p>
 * Principais responsabilidades:
 * <ul>
 *     <li>Desabilitar CSRF (não necessário para APIs REST Stateless).</li>
 *     <li>Configurar rotas públicas (Whitelisting).</li>
 *     <li>Exigir autenticação para todas as demais rotas.</li>
 *     <li>Inserir o filtro JWT antes do filtro padrão de autenticação do usuário.</li>
 * </ul>
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Define a cadeia de filtros de segurança (Security Filter Chain).
     *
     * @param http O objeto HttpSecurity para configuração fluente.
     * @return O SecurityFilterChain construído.
     * @throws Exception Caso ocorra algum erro na configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
