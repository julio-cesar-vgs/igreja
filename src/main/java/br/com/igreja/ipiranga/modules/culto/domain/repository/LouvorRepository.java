package br.com.igreja.ipiranga.modules.culto.domain.repository;

import br.com.igreja.ipiranga.modules.culto.domain.model.Louvor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório para Louvores.
 * Camada: Domain
 */
public interface LouvorRepository extends JpaRepository<Louvor, Long> {
    List<Louvor> findByCultoId(Long cultoId);
}
