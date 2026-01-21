package br.com.igreja.ipiranga.modules.identity.domain.repository;

import br.com.igreja.ipiranga.modules.identity.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import java.util.UUID;

/**
 * Repositório JPA para gerenciamento da entidade {@link Usuario}.
 * <p>
 * Fornece métodos para operações de persistência e consulta.
 * A anotação {@code @TenantId} na entidade garante que todas as buscas aqui sejam
 * filtradas pelo tenant atual automaticamente.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    /**
     * Busca um usuário pelo seu endereço de e-mail.
     *
     * @param email O e-mail a ser pesquisado.
     * @return Um Optional contendo o usuário se encontrado, ou vazio caso contrário.
     */
    Optional<Usuario> findByEmail(String email);
}
