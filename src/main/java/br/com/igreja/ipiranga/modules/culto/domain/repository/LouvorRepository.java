package br.com.igreja.ipiranga.modules.culto.domain.repository;

import br.com.igreja.ipiranga.modules.culto.domain.model.Louvor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório de acesso a dados para a entidade {@link Louvor}.
 *
 * @author Sistema Igreja
 * @version 1.0
 */
public interface LouvorRepository extends JpaRepository<Louvor, Long> {
    /**
     * Lista os louvores entoados em um culto.
     *
     * @param cultoId ID do culto.
     * @return Lista de louvores.
     */
    List<Louvor> findByCultoId(Long cultoId);
}
