package br.com.igreja.ipiranga.modules.audit.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade responsável por registrar a trilha de auditoria (Audit Trail) de
 * alterações de dados.
 * <p>
 * O objetivo é fornecer responsabilidade (accountability) e rastreabilidade
 * sobre quem fez o que e quando.
 * Esta tabela crescerá indefinidamente e pode servir para análise forense ou
 * recuperação de erros operacionais.
 * </p>
 * <p>
 * Consumido principalmente pelo Aspecto de Auditoria
 * {@link br.com.igreja.ipiranga.modules.audit.infrastructure.AuditAspect}.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Entity
@Table(name = "logs_correcoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogCorrecao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Tipo da entidade que sofreu alteração (ex: Culto, Dizimo).
     */
    private String entidadeTipo;

    /**
     * ID da entidade que sofreu alteração.
     */
    private UUID entidadeId;

    /**
     * Ação realizada (INSERT/UPDATE ou DELETE).
     */
    private String acao;

    /**
     * ID do usuário que realizou a ação.
     */
    private UUID usuarioId;

    /**
     * Data e hora da ocorrência.
     */
    private LocalDateTime timestamp;

    /**
     * Representação JSON ou String do estado anterior da entidade (se aplicável).
     */
    @Column(columnDefinition = "TEXT")
    private String valorAntigo;

    /**
     * Representação JSON ou String do novo estado da entidade.
     */
    @Column(columnDefinition = "TEXT")
    private String valorNovo;
}
