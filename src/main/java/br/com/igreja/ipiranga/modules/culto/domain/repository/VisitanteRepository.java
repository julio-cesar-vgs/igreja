package br.com.igreja.ipiranga.modules.culto.domain.repository;

import br.com.igreja.ipiranga.modules.culto.domain.model.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório de acesso a dados para a entidade {@link Visitante}.
 *
 * @author Sistema Igreja
 * @version 1.0
 */
public interface VisitanteRepository extends JpaRepository<Visitante, Long> {
    /**
     * Lista os visitantes de um culto.
     *
     * @param cultoId ID do culto.
     * @return Lista de visitantes.
     */
    List<Visitante> findByCultoId(Long cultoId);
}
