package br.com.igreja.ipiranga.shared.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.TenantId;

import java.util.UUID;

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
    private UUID igrejaId;

    /**
     * Lista de eventos de domínio registrados por esta entidade.
     * Marcado como @Transient para não ser persistido no banco.
     */
    @lombok.ToString.Exclude
    @jakarta.persistence.Transient
    private final java.util.List<DomainEvent> domainEvents = new java.util.ArrayList<>();

    /**
     * Registra um novo evento de domínio.
     * @param event O evento a ser registrado.
     */
    protected void registerEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }

    /**
     * Método utilizado pelo Spring Data para publicar eventos automaticamente após o save().
     * @return Lista de eventos a serem publicados.
     */
    @org.springframework.data.domain.AfterDomainEventPublication
    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    /**
     * Retorna os eventos registrados para publicação.
     * Anotado com @DomainEvents para o Spring Data.
     */
    @org.springframework.data.domain.DomainEvents
    public java.util.Collection<DomainEvent> domainEvents() {
        return java.util.Collections.unmodifiableList(this.domainEvents);
    }
}
