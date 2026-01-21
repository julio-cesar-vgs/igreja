package br.com.igreja.ipiranga.modules.culto.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um hino, corinho ou oportunidade de louvor concedida durante o culto.
 * <p>
 * Registra quem cantou e, opcionalmente, qual hino foi entoado.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Entity
@Table(name = "louvores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Louvor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "culto_id", nullable = false)
    private Culto culto;

    @Column(nullable = false)
    private String pessoaNome;

    private String hinoOpcional;
}
