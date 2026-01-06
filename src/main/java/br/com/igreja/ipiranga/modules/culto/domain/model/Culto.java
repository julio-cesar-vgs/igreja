package br.com.igreja.ipiranga.modules.culto.domain.model;

import br.com.igreja.ipiranga.shared.domain.TenantEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidade de Domínio: Culto
 * Camada: Domain
 * Padrão DDD: Aggregate Root (Raiz de Agregado)
 * 
 * Este é o centro do contexto de Culto. Todas as operações relacionadas ao culto
 * devem idealmente passar por este agregado para garantir a consistência.
 */
@Entity
@Table(name = "cultos")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Culto extends TenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
