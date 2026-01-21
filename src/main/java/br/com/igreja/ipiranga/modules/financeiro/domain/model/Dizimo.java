package br.com.igreja.ipiranga.modules.financeiro.domain.model;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entidade que representa um Dízimo.
 * <p>
 * O dízimo é uma contribuição financeira nominal (identificada).
 * Esta entidade armazena o valor, o nome do dizimista e a qual culto esta contribuição se refere.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
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
/**
 * Entidade que registra a conferência financeira de um culto feira por um tesoureiro.
 * <p>
 * Serve como um mecanismo de controle e segurança ("quatro olhos"), onde um tesoureiro
 * valida e assina digitalmente (pelo ID) o total arrecadado.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
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
