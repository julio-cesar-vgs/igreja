package br.com.igreja.ipiranga.modules.culto.domain.repository;

import br.com.igreja.ipiranga.modules.culto.domain.model.Musico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório para Músicos.
 * Camada: Domain
 * <p>
 * Permite buscar músicos associados a um culto específico.
 */
public interface MusicoRepository extends JpaRepository<Musico, Long> {
    List<Musico> findByCultoId(Long cultoId);
}
