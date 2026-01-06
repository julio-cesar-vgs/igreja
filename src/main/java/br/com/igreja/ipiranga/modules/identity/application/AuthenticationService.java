package br.com.igreja.ipiranga.modules.identity.application;

import br.com.igreja.ipiranga.infrastructure.security.JwtService;
import br.com.igreja.ipiranga.modules.identity.application.dto.AuthResponse;
import br.com.igreja.ipiranga.modules.identity.application.dto.LoginRequest;
import br.com.igreja.ipiranga.modules.identity.application.dto.RegisterRequest;
import br.com.igreja.ipiranga.modules.identity.domain.model.Usuario;
import br.com.igreja.ipiranga.modules.identity.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Service de Aplicação: AuthenticationService
 * Camada: Application
 * 
 * Gerencia o registro de novos usuários e a autenticação.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        var user = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role(request.getRole())
                .build();
        
        // Seta o igreja_id para o multi-tenancy
        user.setIgrejaId(request.getIgrejaId());

        repository.save(user);

        return generateAuthResponse(user);
    }

    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return generateAuthResponse(user);
    }

    private AuthResponse generateAuthResponse(Usuario user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("igreja_id", user.getIgrejaId());
        extraClaims.put("role", user.getRole().name());

        String token = jwtService.generateToken(extraClaims, user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
