package br.com.igreja.ipiranga.modules.financeiro.domain.repository;

import br.com.igreja.ipiranga.modules.financeiro.domain.model.TesoureiroConferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório para gerenciamento da entidade {@link TesoureiroConferencia}.
 *
 * @author Sistema Igreja
 * @version 1.0
 */
public interface TesoureiroConferenciaRepository extends JpaRepository<TesoureiroConferencia, Long> {
    /**
     * Busca a conferência de tesouraria de um culto específico.
     *
     * @param cultoId ID do culto.
     * @return Optional contendo a conferência se existir.
     */
    Optional<TesoureiroConferencia> findByCultoId(Long cultoId);
}
