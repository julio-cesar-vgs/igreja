package br.com.igreja.ipiranga.modules.financeiro.domain.repository;

import br.com.igreja.ipiranga.modules.financeiro.domain.model.Dizimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.UUID;

/**
 * Repositório para gerenciamento da entidade {@link Dizimo}.
 *
 * @author Sistema Igreja
 * @version 1.0
 */
public interface DizimoRepository extends JpaRepository<Dizimo, UUID> {
    /**
     * Lista os dízimos associados a um determinado culto.
     *
     * @param cultoId ID do culto.
     * @return Lista de dízimos.
     */
    List<Dizimo> findByCultoId(UUID cultoId);
}
