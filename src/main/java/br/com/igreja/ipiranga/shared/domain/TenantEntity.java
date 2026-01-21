package br.com.igreja.ipiranga.shared.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.TenantId;

/**
 * Superclasse abstrata base para todas as entidades que requerem suporte a Multi-tenancy (Multilocação).
 * <p>
 * Esta classe utiliza a anotação {@link TenantId} do Hibernate para implementar o isolamento lógico
 * de dados com base na coluna de identificação da locação (tenant). Isso garante que as operações
 * de banco de dados (consultas, atualizações, deleções) sejam automaticamente filtradas pelo contexto
 * da igreja atual.
 * </p>
 * <p>
 * <strong>Camada:</strong> Shared Domain<br>
 * <strong>Padrão:</strong> Layer Supertype / Base Class
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class TenantEntity {

    /**
     * Identificador único da igreja (Tenant) ao qual esta entidade pertence.
     * <p>
     * Este campo é gerenciado automaticamente pelo Hibernate através da anotação {@link TenantId}.
     * Não deve ser manipulado manualmente na maioria dos casos.
     * </p>
     */
    @TenantId
    @Column(name = "igreja_id")
    private Long igrejaId;
}
