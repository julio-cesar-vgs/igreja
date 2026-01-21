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
 * Serviço de aplicação responsável pelos casos de uso de Identidade e Acesso.
 * <p>
 * Centraliza a lógica de negócio para:
 * <ul>
 *     <li>Cadastro de novos usuários (com validação de duplicidade).</li>
 *     <li>Autenticação de usuários existentes (login).</li>
 *     <li>Geração de tokens de acesso (JWT).</li>
 * </ul>
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registra um novo usuário no sistema.
     * <p>
     * O fluxo de registro envolve:
     * <ol>
     *     <li>Validar se o e-mail já existe.</li>
     *     <li>Criar a entidade {@link Usuario} com a senha criptografada.</li>
     *     <li>Associar o usuário à igreja correta (Tenant).</li>
     *     <li>Salvar no banco de dados.</li>
     *     <li>Gerar e retornar o token JWT inicial.</li>
     * </ol>
     * </p>
     *
     * @param request DTO contendo os dados do novo usuário.
     * @return AuthResponse contendo o token de acesso.
     * @throws RuntimeException se o email já estiver cadastrado.
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        // ... (resto do método igual, focado na explicação)
        var user = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role(request.getRole())
                .build();
        
        user.setIgrejaId(request.getIgrejaId());

        repository.save(user);

        return generateAuthResponse(user);
    }

    /**
     * Realiza a autenticação de um usuário.
     * <p>
     * Delega a validação de credenciais para o {@link AuthenticationManager} do Spring Security.
     * Se bem sucedido, busca o usuário completo e gera um token JWT.
     * </p>
     *
     * @param request DTO com email e senha.
     * @return AuthResponse com o token JWT.
     * @throws org.springframework.security.core.AuthenticationException se as credenciais forem inválidas.
     */
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
