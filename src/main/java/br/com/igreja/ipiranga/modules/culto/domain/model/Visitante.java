package br.com.igreja.ipiranga.modules.culto.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade de Domínio: Visitante
 * Camada: Domain
 * 
 * Representa um visitante presente no culto.
 * Importante para o acolhimento e acompanhamento de novos membros.
 */
@Entity
@Table(name = "visitantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visitante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "culto_id", nullable = false)
    private Culto culto;

    @Column(nullable = false)
    private String nome;
}
