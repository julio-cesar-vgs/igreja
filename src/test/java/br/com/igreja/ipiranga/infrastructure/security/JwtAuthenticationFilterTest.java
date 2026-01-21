package br.com.igreja.ipiranga.infrastructure.security;

import br.com.igreja.ipiranga.infrastructure.tenant.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
        TenantContext.clear();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
        TenantContext.clear();
    }

    @Test
    void doFilterInternal_WithoutAuthorizationHeader_ShouldContinueFilterChain() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, never()).extractUsername(anyString());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void doFilterInternal_WithInvalidAuthorizationHeader_ShouldContinueFilterChain() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("InvalidHeader");

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, never()).extractUsername(anyString());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void doFilterInternal_WithValidToken_ShouldAuthenticateUser() throws ServletException, IOException {
        // Arrange
        String token = "validToken";
        String userEmail = "test@example.com";
        Long igrejaId = 1L;

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);
        when(jwtService.extractIgrejaId(token)).thenReturn(igrejaId);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService, times(1)).extractUsername(token);
        verify(userDetailsService, times(1)).loadUserByUsername(userEmail);
        verify(jwtService, times(1)).isTokenValid(token, userDetails);
        verify(jwtService, times(1)).extractIgrejaId(token);
        verify(filterChain, times(1)).doFilter(request, response);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isEqualTo(userDetails);
    }

    @Test
    void doFilterInternal_WithInvalidToken_ShouldNotAuthenticateUser() throws ServletException, IOException {
        // Arrange
        String token = "invalidToken";
        String userEmail = "test@example.com";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(false);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService, times(1)).extractUsername(token);
        verify(userDetailsService, times(1)).loadUserByUsername(userEmail);
        verify(jwtService, times(1)).isTokenValid(token, userDetails);
        verify(jwtService, never()).extractIgrejaId(anyString());
        verify(filterChain, times(1)).doFilter(request, response);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void doFilterInternal_WithAlreadyAuthenticatedUser_ShouldNotReauthenticate() throws ServletException, IOException {
        // Arrange
        String token = "validToken";
        String userEmail = "test@example.com";

        SecurityContextHolder.getContext().setAuthentication(
                mock(org.springframework.security.core.Authentication.class)
        );

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(userEmail);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService, times(1)).extractUsername(token);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_WithNullUsername_ShouldNotAuthenticate() throws ServletException, IOException {
        // Arrange
        String token = "tokenWithoutUsername";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService, times(1)).extractUsername(token);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain, times(1)).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void doFilterInternal_ShouldClearTenantContextInFinally() throws ServletException, IOException {
        // Arrange
        String token = "validToken";
        String userEmail = "test@example.com";
        Long igrejaId = 1L;

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);
        when(jwtService.extractIgrejaId(token)).thenReturn(igrejaId);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertThat(TenantContext.getCurrentTenant()).isNull();
    }

    @Test
    void doFilterInternal_WhenFilterChainThrowsException_ShouldStillClearTenantContext() throws ServletException, IOException {
        // Arrange
        String token = "validToken";
        String userEmail = "test@example.com";
        Long igrejaId = 1L;

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);
        when(jwtService.extractIgrejaId(token)).thenReturn(igrejaId);
        doThrow(new ServletException("Test exception")).when(filterChain).doFilter(request, response);

        // Act & Assert
        try {
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        } catch (ServletException e) {
            assertThat(e.getMessage()).isEqualTo("Test exception");
        }

        assertThat(TenantContext.getCurrentTenant()).isNull();
    }
}
