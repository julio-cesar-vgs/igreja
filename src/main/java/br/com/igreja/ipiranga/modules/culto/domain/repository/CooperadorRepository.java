package br.com.igreja.ipiranga.modules.culto.domain.repository;

import br.com.igreja.ipiranga.modules.culto.domain.model.Cooperador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório para Cooperadores.
 * Camada: Domain
 * 
 * Permite buscar cooperadores associados a um culto específico.
 */
public interface CooperadorRepository extends JpaRepository<Cooperador, Long> {
    List<Cooperador> findByCultoId(Long cultoId);
}
