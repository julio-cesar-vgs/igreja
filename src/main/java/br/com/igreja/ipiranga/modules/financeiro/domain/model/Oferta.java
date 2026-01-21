package br.com.igreja.ipiranga.modules.financeiro.domain.model;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entidade que representa uma Oferta.
 * <p>
 * Diferente do dízimo, a oferta geralmente é anônima (embora no modelo atual não tenhamos campo de nome, o contexto e uso são distintos).
 * Registra entradas financeiras esporádicas arrecadadas durante um culto.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Entity
@Table(name = "ofertas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "culto_id", nullable = false)
    private Culto culto;

    @Column(nullable = false)
    private BigDecimal valor;
}
