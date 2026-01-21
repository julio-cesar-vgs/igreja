package br.com.igreja.ipiranga.modules.financeiro.domain.model;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entidade de Domínio: Dizimo
 * Camada: Domain
 * Contexto: Financeiro
 * 
 * Representa uma contribuição financeira identificada.
 */
@Entity
@Table(name = "dizimos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dizimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "culto_id", nullable = false)
    private Culto culto;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal valor;
}
