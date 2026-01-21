package br.com.igreja.ipiranga.infrastructure.security;

import br.com.igreja.ipiranga.infrastructure.tenant.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Filtro de segurança que intercepta todas as requisições HTTP para autenticação via JWT.
 * <p>
 * Este filtro é executado uma vez por requisição (OncePerRequestFilter) e realiza as seguintes operações:
 * <ol>
 *     <li>Verifica a presença do cabeçalho 'Authorization' com o prefixo 'Bearer '.</li>
 *     <li>Extrai e valida o token JWT.</li>
 *     <li>Carrega os detalhes do usuário associado ao token.</li>
 *     <li>Estabelece o contexto de segurança do Spring (SecurityContext).</li>
 *     <li>Define o contexto de tenant (Igreja) para a thread atual, permitindo isolamento de dados.</li>
 * </ol>
 * Caso o token token seja inválido ou inexistente, a requisição segue o fluxo sem autenticação
 * (que poderá ser barrada posteriormente pelo SecurityFilterChain se o endpoint for protegido).
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Lógica principal do filtro de autenticação.
     *
     * @param request A requisição HTTP recebida.
     * @param response A resposta HTTP sendo construída.
     * @param filterChain A cadeia de filtros do Spring Security.
     * @throws ServletException Em caso de erros de servlet.
     * @throws IOException Em caso de erros de I/O.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        String userEmail = null;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        try {
            userEmail = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            // Token inválido ou expirado
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UUID igrejaId = jwtService.extractIgrejaId(jwt);
                TenantContext.setCurrentTenant(igrejaId);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
