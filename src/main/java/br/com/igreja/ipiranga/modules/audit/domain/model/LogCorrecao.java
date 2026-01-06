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

    private String entidadeTipo;
    private Long entidadeId;
    private String acao;
    private Long usuarioId;
    private LocalDateTime timestamp;
    
    @Column(columnDefinition = "TEXT")
    private String valorAntigo;
    
    @Column(columnDefinition = "TEXT")
    private String valorNovo;
}
