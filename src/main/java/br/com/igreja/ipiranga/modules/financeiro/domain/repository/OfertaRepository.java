package br.com.igreja.ipiranga.modules.financeiro.domain.repository;

import br.com.igreja.ipiranga.modules.financeiro.domain.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositório para Ofertas.
 * Camada: Domain
 */
public interface OfertaRepository extends JpaRepository<Oferta, Long> {
    List<Oferta> findByCultoId(Long cultoId);
}
