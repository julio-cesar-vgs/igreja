package br.com.igreja.ipiranga.modules.culto.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade de Domínio: Louvor
 * Camada: Domain
 * 
 * Representa um momento de louvor ou hino cantado durante o culto.
 * Faz parte do Agregado Culto, mas é persistido em sua própria tabela.
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
