package br.com.igreja.ipiranga.modules.culto.domain.repository;

import br.com.igreja.ipiranga.modules.culto.domain.model.Presbitero;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositório para Presbíteros.
 * Camada: Domain
 */
public interface PresbiteroRepository extends JpaRepository<Presbitero, Long> {
    List<Presbitero> findByCultoId(Long cultoId);
}
