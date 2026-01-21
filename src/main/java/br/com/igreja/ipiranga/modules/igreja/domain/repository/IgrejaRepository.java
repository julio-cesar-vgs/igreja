package br.com.igreja.ipiranga.modules.igreja.domain.repository;

import br.com.igreja.ipiranga.modules.igreja.domain.model.Igreja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Interface de Repositório para gerenciamento da entidade {@link Igreja}.
 * <p>
 * Fornece métodos CRUD padrão via JpaRepository.
 * Diferente de outros repositórios, este lida com a entidade que define o Tenant,
 * portanto, suas consultas geralmente não são filtradas por tenant (é um cadastro global).
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
public interface IgrejaRepository extends JpaRepository<Igreja, UUID> {
}
