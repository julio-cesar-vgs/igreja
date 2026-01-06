package br.com.igreja.ipiranga.modules.culto.application.dto;

import br.com.igreja.ipiranga.modules.culto.domain.model.*;
import br.com.igreja.ipiranga.modules.financeiro.domain.model.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para o Dashboard Consolidado do Culto.
 * Camada: Application
 * 
 * Agrega informações de múltiplos contextos (Culto e Financeiro) para visualização.
 */
@Data
@Builder
public class CultoDashboardDTO {
    private Culto culto;
    private List<Louvor> louvores;
    private List<Cooperador> cooperadores;
    private List<Musico> musicos;
    private List<Presbitero> presbiteros;
    private List<Visitante> visitantes;
    
    // Financeiro
    private List<Dizimo> dizimos;
    private List<Oferta> ofertas;
    private BigDecimal totalDizimos;
    private BigDecimal totalOfertas;
    private BigDecimal totalGeral;
    
    // Contagens
    private int totalPessoas;
    
    // Conferência
    private TesoureiroConferencia conferencia;
}
