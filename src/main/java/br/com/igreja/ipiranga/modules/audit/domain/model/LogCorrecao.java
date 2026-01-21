package br.com.igreja.ipiranga.modules.audit.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade de Domínio: LogCorrecao
 * Camada: Domain
 * Contexto: Audit
 * 
 * Registra trilhas de auditoria para alterações em entidades do sistema.
 * Esta entidade é fundamental para rastreabilidade e segurança, armazenando
 * o estado anterior e o novo estado das entidades modificadas.
 * <p>
 * Consumido por: AuditAspect (via AOP)
 */
@Entity
@Table(name = "logs_correcoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogCorrecao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Tipo da entidade que sofreu alteração (ex: Culto, Dizimo).
     */
    private String entidadeTipo;

    /**
     * ID da entidade que sofreu alteração.
     */
    private Long entidadeId;

    /**
     * Ação realizada (INSERT/UPDATE ou DELETE).
     */
    private String acao;

    /**
     * ID do usuário que realizou a ação.
     */
    private Long usuarioId;

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
