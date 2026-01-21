package br.com.igreja.ipiranga.modules.identity.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto com as credenciais para solicitação de login.
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    /**
     * E-mail cadastrado do usuário.
     */
    private String email;

    /**
     * Senha em texto plano (será validada contra o hash no banco).
     */
    private String password;
}
