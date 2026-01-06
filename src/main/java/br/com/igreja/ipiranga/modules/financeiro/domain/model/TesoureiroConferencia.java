package br.com.igreja.ipiranga.modules.financeiro.domain.model;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tesoureiro_conferencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TesoureiroConferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "culto_id", nullable = false)
    private Culto culto;

    private Long tesoureiroId;

    private BigDecimal totalConferido;
}
