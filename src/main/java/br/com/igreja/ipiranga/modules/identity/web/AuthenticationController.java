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
 * Controller REST: AuthenticationController
 * Camada: Web
 * 
 * Endpoints públicos para autenticação e registro de usuários.
 * Permite que novos usuários se cadastrem e que usuários existentes obtenham tokens JWT para acesso.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para registro e login de usuários")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @Operation(summary = "Registrar um novo usuário", description = "Cria um novo usuário e retorna o token JWT")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário", description = "Valida as credenciais e retorna o token JWT")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
