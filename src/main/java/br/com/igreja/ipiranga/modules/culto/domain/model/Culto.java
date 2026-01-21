package br.com.igreja.ipiranga.modules.culto.domain.model;

import br.com.igreja.ipiranga.shared.domain.TenantEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade Raiz de Agregado (Aggregate Root) que representa um Culto Oficial.
 * <p>
 * O Culto é o evento central da igreja e esta entidade agrupa todas as informações relacionadas a ele,
 * servindo como ponto de entrada para consistência transacional do agregado.
 * </p>
 * <p>
 * Herda de {@link TenantEntity} para garantir isolamento por filial/igreja.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Entity
@Table(name = "cultos")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Culto extends TenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    private String tema;

    private String palavraInicialPor;

    private String palavraFinalPor;

    private Integer totalPessoas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCulto status;

    public enum StatusCulto {
        EM_ANDAMENTO, FINALIZADO
    }

    /**
     * Método de conveniência para registrar atualização.
     * Idealmente seria chamado em métodos de mudança de estado, mas como usamos anemismo (Setters),
     * chamaremos manualmente no Service.
     */
    public void registrarAtualizacao() {
        this.registerEvent(new br.com.igreja.ipiranga.modules.culto.domain.event.CultoAtualizado(this));
    }
}
