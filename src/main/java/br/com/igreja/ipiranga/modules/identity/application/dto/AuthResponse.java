package br.com.igreja.ipiranga.modules.identity.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de Transferência de Dados (DTO) para enviar o token de autenticação ao cliente.
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    /**
     * Token JWT (Json Web Token) que deve ser enviado no cabeçalho 'Authorization: Bearer <token>' para requisições subsequentes.
     */
    private String token;
}
