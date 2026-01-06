package br.com.igreja.ipiranga.modules.audit.domain.repository;

import br.com.igreja.ipiranga.modules.audit.domain.model.LogCorrecao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para Logs de Auditoria.
 * Camada: Domain
 */
public interface LogCorrecaoRepository extends JpaRepository<LogCorrecao, Long> {
}
