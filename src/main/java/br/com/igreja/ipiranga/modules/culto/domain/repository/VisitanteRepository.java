package br.com.igreja.ipiranga.modules.culto.domain.repository;

import br.com.igreja.ipiranga.modules.culto.domain.model.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositório para Visitantes.
 * Camada: Domain
 */
public interface VisitanteRepository extends JpaRepository<Visitante, Long> {
    List<Visitante> findByCultoId(Long cultoId);
}
