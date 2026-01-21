package br.com.igreja.ipiranga.modules.audit.domain.repository;

import br.com.igreja.ipiranga.modules.audit.domain.model.LogCorrecao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de Repositório JPA para persistência de logs de auditoria.
 * <p>
 * Geralmente usado apenas para inserção (logs são write-once) e consultas de relatórios.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
public interface LogCorrecaoRepository extends JpaRepository<LogCorrecao, Long> {
}
