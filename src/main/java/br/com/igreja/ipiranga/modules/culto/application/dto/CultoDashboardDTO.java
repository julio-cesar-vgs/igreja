package br.com.igreja.ipiranga.modules.culto.application.dto;

import br.com.igreja.ipiranga.modules.culto.domain.model.*;
import br.com.igreja.ipiranga.modules.financeiro.domain.model.Dizimo;
import br.com.igreja.ipiranga.modules.financeiro.domain.model.Oferta;
import br.com.igreja.ipiranga.modules.financeiro.domain.model.TesoureiroConferencia;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO (Objeto de Transferência de Dados) rico que consolida toda a visão operacional de um culto.
 * <p>
 * Projetado para otimizar o carregamento de dados no frontend, evitando o problema de "N+1 request"
 * onde a UI precisaria chamar endpoints separados para louvores, ofertas, dízimos, etc.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Data
@Builder
public class CultoDashboardDTO {
    /**
     * Objeto raiz com dados básicos do culto (Data, Tema, Dirigentes).
     */
    private Culto culto;
    private List<Louvor> louvores;
    private List<Cooperador> cooperadores;
    private List<Musico> musicos;
    private List<Presbitero> presbiteros;
    private List<Visitante> visitantes;
    
    // Financeiro
    private List<Dizimo> dizimos;
    private List<Oferta> ofertas;
    /**
     * Valor total somado de todos os dízimos lançados.
     */
    private BigDecimal totalDizimos;

    /**
     * Valor total somado de todas as ofertas lançadas.
     */
    private BigDecimal totalOfertas;

    /**
     * Valor total geral arrecadado (Dízimos + Ofertas).
     */
    private BigDecimal totalGeral;
    
    // Contagens
    private int totalPessoas;
    
    // Conferência
    private TesoureiroConferencia conferencia;
}
