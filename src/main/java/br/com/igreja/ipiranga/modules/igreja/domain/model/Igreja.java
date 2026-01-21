package br.com.igreja.ipiranga.modules.igreja.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Representa uma unidade física da organização religiosa (Igreja).
 * <p>
 * Esta entidade é o pilar central do sistema de Multi-tenancy. Cada registro no banco de dados
 * (exceto tabelas de sistema) deve pertencer a uma Igreja.
 * Pode ser do tipo MATRIZ (sede administrativa) ou FILIAL (congregações locais).
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Entity
@Table(name = "igrejas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Igreja {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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
