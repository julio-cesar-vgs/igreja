package br.com.igreja.ipiranga.modules.identity.web;

import br.com.igreja.ipiranga.modules.identity.application.AuthenticationService;
import br.com.igreja.ipiranga.modules.identity.application.dto.AuthResponse;
import br.com.igreja.ipiranga.modules.identity.application.dto.LoginRequest;
import br.com.igreja.ipiranga.modules.identity.application.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para gerenciamento de identidade.
 * <p>
 * Expõe endpoints públicos para que usuários possam se registrar e realizar login.
 * Esta é a porta de entrada para a obtenção do token Bearer necessário para acessar
 * os demais recursos da API.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para registro e login de usuários")
public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * Endpoint para cadastro de novos usuários.
     *
     * @param request Objeto JSON contendo nome, email, senha, igrejaId e role.
     * @return ResponseEntity contendo o token de acesso gerado e status HTTP 200 (OK).
     */
    @PostMapping("/register")
    @Operation(summary = "Registrar um novo usuário", description = "Cria um novo usuário e retorna o token JWT")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * Endpoint para autenticação (Login).
     *
     * @param request Objeto JSON contendo email e senha.
     * @return ResponseEntity contendo o token de acesso gerado e status HTTP 200 (OK).
     */
    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário", description = "Valida as credenciais e retorna o token JWT")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
