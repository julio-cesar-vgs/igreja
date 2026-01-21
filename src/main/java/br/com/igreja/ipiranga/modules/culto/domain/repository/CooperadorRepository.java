package br.com.igreja.ipiranga.modules.culto.domain.repository;

import br.com.igreja.ipiranga.modules.culto.domain.model.Cooperador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.UUID;

/**
 * Repositório de acesso a dados para a entidade {@link Cooperador}.
 *
 * @author Sistema Igreja
 * @version 1.0
 */
public interface CooperadorRepository extends JpaRepository<Cooperador, UUID> {
    /**
     * Busca todos os cooperadores vinculados a um determinado culto.
     *
     * @param cultoId ID do culto.
     * @return Lista de cooperadores.
     */
    List<Cooperador> findByCultoId(UUID cultoId);
}
