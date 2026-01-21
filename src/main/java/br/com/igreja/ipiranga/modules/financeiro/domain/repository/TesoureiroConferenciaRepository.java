package br.com.igreja.ipiranga.modules.financeiro.domain.repository;

import br.com.igreja.ipiranga.modules.financeiro.domain.model.TesoureiroConferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório para Conferências de Tesouraria.
 * Camada: Domain
 */
public interface TesoureiroConferenciaRepository extends JpaRepository<TesoureiroConferencia, Long> {
    Optional<TesoureiroConferencia> findByCultoId(Long cultoId);
}
