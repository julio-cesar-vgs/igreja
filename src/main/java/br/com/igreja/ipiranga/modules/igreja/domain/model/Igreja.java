package br.com.igreja.ipiranga.modules.igreja.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade de Domínio: Igreja
 * Camada: Domain
 * Contexto: Cadastro / Configuração
 * 
 * Representa uma unidade física da organização (Matriz ou Filial).
 * Fundamental para o sistema Multi-tenant, pois cada registro de dados (Culto, Membro, Financeiro)
 * estará associado a uma instância desta entidade.
 */
@Entity
@Table(name = "igrejas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Igreja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoIgreja tipo;

    private String endereco;

    public enum TipoIgreja {
        MATRIZ, FILIAL
    }
}
