package br.com.igreja.ipiranga.modules.culto.domain.repository;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório principal para a entidade {@link Culto}.
 * <p>
 * Fornece os métodos CRUD padrão para a raiz do agregado.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
public interface CultoRepository extends JpaRepository<Culto, Long> {
}
