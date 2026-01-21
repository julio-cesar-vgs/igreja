package br.com.igreja.ipiranga.modules.culto.domain.model;

import br.com.igreja.ipiranga.shared.domain.TenantEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Entidade de Domínio: Culto
 * Camada: Domain
 * Padrão DDD: Aggregate Root (Raiz de Agregado)
 * 
 * Representa um evento de culto realizado pela igreja.
 * Este é o centro do contexto de Culto. Todas as operações relacionadas ao culto
 * (como adicionar louvores, registrar ofertas) são logicamente agrupadas em torno deste agregado.
 * 
 * Herda de TenantEntity para garantir o isolamento dos dados por igreja (Multi-tenancy).
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
