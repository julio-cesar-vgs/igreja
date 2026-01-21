package br.com.igreja.ipiranga.shared.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.TenantId;

/**
 * Superclasse de Entidade de Tenant.
 * Camada: Shared Domain
 *
 * Fornece isolamento de dados por igreja em todo o sistema usando @TenantId do Hibernate.
 */
@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class TenantEntity {

    @TenantId
    @Column(name = "igreja_id")
    private Long igrejaId;
}
