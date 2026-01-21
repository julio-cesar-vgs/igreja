package br.com.igreja.ipiranga.modules.identity.web;

import br.com.igreja.ipiranga.infrastructure.security.JwtService;
import br.com.igreja.ipiranga.modules.identity.application.AuthenticationService;
import br.com.igreja.ipiranga.modules.identity.application.dto.AuthResponse;
import br.com.igreja.ipiranga.modules.identity.application.dto.LoginRequest;
import br.com.igreja.ipiranga.modules.identity.application.dto.RegisterRequest;
import br.com.igreja.ipiranga.modules.identity.domain.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthenticationService authenticationService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private AuthResponse authResponse;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequest.builder()
                .nome("João Silva")
                .email("joao@example.com")
                .senha("senha123")
                .igrejaId(1L)
                .role(Usuario.Role.ROLE_USER)
                .build();

        loginRequest = LoginRequest.builder()
                .email("joao@example.com")
                .password("senha123")
                .build();

        authResponse = AuthResponse.builder()
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.test.token")
                .build();
    }

    @Test
    void testRegister_Success() throws Exception {
        // Given
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value(authResponse.getToken()));

        verify(authenticationService, times(1)).register(any(RegisterRequest.class));
    }

    @Test
    void testRegister_WithValidData() throws Exception {
        // Given
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isNotEmpty());

        verify(authenticationService).register(any(RegisterRequest.class));
    }

    @Test
    void testRegister_WithInvalidEmail() throws Exception {
        // Given
        registerRequest.setEmail("invalid-email");

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());

        verify(authenticationService, never()).register(any(RegisterRequest.class));
    }

    @Test
    void testRegister_WithBlankNome() throws Exception {
        // Given
        registerRequest.setNome("");

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());

        verify(authenticationService, never()).register(any(RegisterRequest.class));
    }

    @Test
    void testRegister_WithNullIgrejaId() throws Exception {
        // Given
        registerRequest.setIgrejaId(null);

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());

        verify(authenticationService, never()).register(any(RegisterRequest.class));
    }

    @Test
    void testRegister_WithNullRole() throws Exception {
        // Given
        registerRequest.setRole(null);

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());

        verify(authenticationService, never()).register(any(RegisterRequest.class));
    }

    @Test
    void testRegister_ServiceThrowsException() throws Exception {
        // Given
        when(authenticationService.register(any(RegisterRequest.class)))
                .thenThrow(new RuntimeException("Email já cadastrado"));

        // When & Then
        try {
            mockMvc.perform(post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().is5xxServerError());
        } catch (Exception e) {
            // Expected exception if no global exception handler is present
            if (!(e.getCause() instanceof RuntimeException) || !e.getCause().getMessage().equals("Email já cadastrado")) {
                throw e;
            }
        }

        verify(authenticationService).register(any(RegisterRequest.class));
    }

    @Test
    void testAuthenticate_Success() throws Exception {
        // Given
        when(authenticationService.authenticate(any(LoginRequest.class))).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value(authResponse.getToken()));

        verify(authenticationService, times(1)).authenticate(any(LoginRequest.class));
    }

    @Test
    void testAuthenticate_WithValidCredentials() throws Exception {
        // Given
        when(authenticationService.authenticate(any(LoginRequest.class))).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isNotEmpty());

        verify(authenticationService).authenticate(any(LoginRequest.class));
    }

    @Test
    void testAuthenticate_ServiceThrowsException() throws Exception {
        // Given
        when(authenticationService.authenticate(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Usuário não encontrado"));

        // When & Then
        try {
            mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().is5xxServerError());
        } catch (Exception e) {
             // Expected exception if no global exception handler is present
            if (!(e.getCause() instanceof RuntimeException) || !e.getCause().getMessage().equals("Usuário não encontrado")) {
                throw e;
            }
        }

        verify(authenticationService).authenticate(any(LoginRequest.class));
    }

    @Test
    void testRegister_WithRoleAdmin() throws Exception {
        // Given
        registerRequest.setRole(Usuario.Role.ROLE_ADMIN);
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(authResponse.getToken()));

        verify(authenticationService).register(any(RegisterRequest.class));
    }

    @Test
    void testRegister_WithRoleTesoureiro() throws Exception {
        // Given
        registerRequest.setRole(Usuario.Role.ROLE_TESOUREIRO);
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(authResponse.getToken()));

        verify(authenticationService).register(any(RegisterRequest.class));
    }

    @Test
    void testRegister_WithDifferentIgrejaId() throws Exception {
        // Given
        registerRequest.setIgrejaId(99L);
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        verify(authenticationService).register(any(RegisterRequest.class));
    }

    @Test
    void testAuthenticate_WithDifferentEmail() throws Exception {
        // Given
        loginRequest.setEmail("outro@example.com");
        when(authenticationService.authenticate(any(LoginRequest.class))).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        verify(authenticationService).authenticate(any(LoginRequest.class));
    }
}
