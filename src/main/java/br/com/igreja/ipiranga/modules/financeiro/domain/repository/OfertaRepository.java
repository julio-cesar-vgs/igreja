package br.com.igreja.ipiranga.modules.financeiro.domain.repository;

import br.com.igreja.ipiranga.modules.financeiro.domain.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.UUID;

/**
 * Repositório para gerenciamento da entidade {@link Oferta}.
 *
 * @author Sistema Igreja
 * @version 1.0
 */
public interface OfertaRepository extends JpaRepository<Oferta, UUID> {
    /**
     * Lista as ofertas arrecadadas em um determinado culto.
     *
     * @param cultoId ID do culto.
     * @return Lista de ofertas.
     */
    List<Oferta> findByCultoId(UUID cultoId);
}
