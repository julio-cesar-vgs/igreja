package br.com.igreja.ipiranga.modules.financeiro.domain.model;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entidade que registra a conferência financeira de um culto feita por um tesoureiro.
 * <p>
 * Serve como um mecanismo de controle e segurança ("quatro olhos"), onde um tesoureiro
 * valida e assina digitalmente (pelo ID) o total arrecadado.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Entity
@Table(name = "tesoureiro_conferencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TesoureiroConferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "culto_id", nullable = false)
    private Culto culto;

    private UUID tesoureiroId;

    private BigDecimal totalConferido;

    @Enumerated(EnumType.STRING)
    private StatusConferencia status;

    private BigDecimal diferenca;

    public enum StatusConferencia {
        CONFERIDO,
        DIVERGENTE
    }
}
