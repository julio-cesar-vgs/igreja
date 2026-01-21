package br.com.igreja.ipiranga.modules.audit.domain.repository;

import br.com.igreja.ipiranga.modules.audit.domain.model.LogCorrecao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para Logs de Auditoria.
 * Camada: Domain
 * 
 * Interface que estende JpaRepository para fornecer operações de CRUD
 * para a entidade LogCorrecao.
 * 
 * Utilizado principalmente pelo AuditAspect para persistir os logs gerados automaticamente.
 */
public interface LogCorrecaoRepository extends JpaRepository<LogCorrecao, Long> {
}
