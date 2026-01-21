package br.com.igreja.ipiranga.modules.igreja.domain.repository;

import br.com.igreja.ipiranga.modules.igreja.domain.model.Igreja;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para a entidade Igreja.
 * Camada: Domain
 * 
 * Fornece acesso aos dados das igrejas cadastradas no sistema.
 */
public interface IgrejaRepository extends JpaRepository<Igreja, Long> {
}
