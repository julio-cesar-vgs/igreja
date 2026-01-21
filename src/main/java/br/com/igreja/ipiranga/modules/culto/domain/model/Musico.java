package br.com.igreja.ipiranga.modules.culto.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade de Domínio: Musico
 * Camada: Domain
 * 
 * Representa um músico que participou do culto.
 * Utilizado para registro de escala e participação.
 */
@Entity
@Table(name = "musicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Musico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "culto_id", nullable = false)
    private Culto culto;

    @Column(nullable = false)
    private String nome;
}
