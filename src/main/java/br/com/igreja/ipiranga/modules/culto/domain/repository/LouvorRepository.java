package br.com.igreja.ipiranga.modules.culto.domain.repository;

import br.com.igreja.ipiranga.modules.culto.domain.model.Louvor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório para Louvores.
 * Camada: Domain
 * 
 * Permite buscar louvores associados a um culto específico.
 */
public interface LouvorRepository extends JpaRepository<Louvor, Long> {
    List<Louvor> findByCultoId(Long cultoId);
}
