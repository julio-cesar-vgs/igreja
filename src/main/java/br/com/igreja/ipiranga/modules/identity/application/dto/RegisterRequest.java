package br.com.igreja.ipiranga.modules.identity.application.dto;

import br.com.igreja.ipiranga.modules.identity.domain.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO contendo os dados necessários para cadastrar um novo usuário no sistema.
 * <p>
 * Inclui validações via Bean Validation para garantir integridade básica dados.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    /**
     * Nome completo do usuário. Obrigatório.
     */
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    /**
     * Endereço de e-mail válido. Será usado como login.
     */
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    /**
     * Senha secreta do usuário. Idealmente deveria ter regras de complexidade.
     */
    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    /**
     * ID da igreja à qual este usuário será vinculado.
     */
    @NotNull(message = "O ID da igreja é obrigatório")
    private Long igrejaId;

    /**
     * Perfil de acesso do usuário (ADMIN, USER, TESOUREIRO).
     */
    @NotNull(message = "O perfil (role) é obrigatório")
    private Usuario.Role role;
}
