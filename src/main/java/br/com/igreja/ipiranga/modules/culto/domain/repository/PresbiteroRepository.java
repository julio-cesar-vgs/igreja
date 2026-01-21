package br.com.igreja.ipiranga.modules.culto.domain.repository;

import br.com.igreja.ipiranga.modules.culto.domain.model.Presbitero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório de acesso a dados para a entidade {@link Presbitero}.
 *
 * @author Sistema Igreja
 * @version 1.0
 */
public interface PresbiteroRepository extends JpaRepository<Presbitero, Long> {
    /**
     * Busca os presbíteros presentes em um culto.
     *
     * @param cultoId ID do culto.
     * @return Lista de presbíteros.
     */
    List<Presbitero> findByCultoId(Long cultoId);
}
