package br.com.igreja.ipiranga.modules.financeiro.domain.repository;

import br.com.igreja.ipiranga.modules.financeiro.domain.model.Dizimo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositório para Dízimos.
 * Camada: Domain
 */
public interface DizimoRepository extends JpaRepository<Dizimo, Long> {
    List<Dizimo> findByCultoId(Long cultoId);
}
