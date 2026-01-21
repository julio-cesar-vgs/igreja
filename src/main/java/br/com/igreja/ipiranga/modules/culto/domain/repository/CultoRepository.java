package br.com.igreja.ipiranga.modules.culto.domain.repository;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de Repositório para o Agregado Culto.
 * Camada: Domain (Contrato)
 * 
 * Define as operações de persistência disponíveis para a entidade Culto.
 * A implementação é fornecida automaticamente pelo Spring Data JPA.
 */
public interface CultoRepository extends JpaRepository<Culto, Long> {
}
