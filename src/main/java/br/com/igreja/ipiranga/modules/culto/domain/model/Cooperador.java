package br.com.igreja.ipiranga.modules.culto.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um Cooperador (obreiro) escalado ou participante do culto.
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Entity
@Table(name = "cooperadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cooperador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "culto_id", nullable = false)
    private Culto culto;

    @Column(nullable = false)
    private String nome;

    private String cargo;
}
