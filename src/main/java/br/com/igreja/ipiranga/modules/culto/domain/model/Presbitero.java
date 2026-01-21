package br.com.igreja.ipiranga.modules.culto.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade de Domínio: Presbitero
 * Camada: Domain
 * 
 * Representa um presbítero presente ou atuante no culto.
 */
@Entity
@Table(name = "presbiteros")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Presbitero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "culto_id", nullable = false)
    private Culto culto;

    @Column(nullable = false)
    private String nome;
}
