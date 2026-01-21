package br.com.igreja.ipiranga.modules.culto.domain.repository;

import br.com.igreja.ipiranga.modules.culto.domain.model.Musico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório de acesso a dados para a entidade {@link Musico}.
 *
 * @author Sistema Igreja
 * @version 1.0
 */
public interface MusicoRepository extends JpaRepository<Musico, Long> {
    /**
     * Recupera a lista de músicos que participaram de um culto.
     *
     * @param cultoId ID do culto.
     * @return Lista de musicos.
     */
    List<Musico> findByCultoId(Long cultoId);
}
