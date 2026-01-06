package br.com.igreja.ipiranga.modules.culto.domain.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade de Domínio: Louvor
 * Camada: Domain
 * 
 * Faz parte do Agregado Culto (Entity).
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
