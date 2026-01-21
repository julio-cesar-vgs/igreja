package br.com.igreja.ipiranga.modules.culto.application;

import br.com.igreja.ipiranga.modules.culto.application.dto.CultoDashboardDTO;
import br.com.igreja.ipiranga.modules.culto.domain.model.*;
import br.com.igreja.ipiranga.modules.culto.domain.repository.*;
import br.com.igreja.ipiranga.modules.financeiro.domain.model.Dizimo;
import br.com.igreja.ipiranga.modules.financeiro.domain.model.Oferta;
import br.com.igreja.ipiranga.modules.financeiro.domain.model.TesoureiroConferencia;
import br.com.igreja.ipiranga.modules.financeiro.domain.repository.DizimoRepository;
import br.com.igreja.ipiranga.modules.financeiro.domain.repository.OfertaRepository;
import br.com.igreja.ipiranga.modules.financeiro.domain.repository.TesoureiroConferenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Serviço de Aplicação especializado na gestão dos detalhes e itens que compõem um culto.
 * <p>
 * Enquanto o {@link CultoApplicationService} cuida do cabeçalho do evento (data, horário, tema),
 * este serviço cuida do conteúdo: quem cantou, quem tocou, quanto foi arrecadado, quem visitou.
 * </p>
 * <p>
 * Atua como um Facade que orquestra chamadas para múltiplos repositórios (Culto, Financeiro, Pessoas)
 * e consolida essas informações (Pattern Aggregator) para consumo do front-end.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class DetalheCultoApplicationService {

    private final LouvorRepository louvorRepository;
    private final DizimoRepository dizimoRepository;
    private final OfertaRepository ofertaRepository;
    private final CooperadorRepository cooperadorRepository;
    private final MusicoRepository musicoRepository;
    private final VisitanteRepository visitanteRepository;
    private final PresbiteroRepository presbiteroRepository;
    private final TesoureiroConferenciaRepository tesoureiroConferenciaRepository;
    private final CultoRepository cultoRepository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Gera o Dashboard unificado do culto.
     * <p>
     * Este método é intensivo em leitura, agregando dados de 9 (nove) repositórios diferentes
     * para montar uma visão completa do estado atual do culto.
     * Realiza também os cálculos sumarizados de totais financeiros (Dízimos + Ofertas).
     * </p>
     *
     * @param cultoId O ID do culto para o qual o dashboard será gerado.
     * @return Um objeto {@link CultoDashboardDTO} populado.
     * @throws RuntimeException caso o culto não exista.
     */
    @Cacheable(value = "dashboards", key = "#cultoId")
    public CultoDashboardDTO getDashboard(UUID cultoId) {
        Culto culto = cultoRepository.findById(cultoId)
                .orElseThrow(() -> new RuntimeException("Culto não encontrado"));

        List<Louvor> louvores = louvorRepository.findByCultoId(cultoId);
        List<Dizimo> dizimos = dizimoRepository.findByCultoId(cultoId);
        List<Oferta> ofertas = ofertaRepository.findByCultoId(cultoId);
        List<Cooperador> cooperadores = cooperadorRepository.findByCultoId(cultoId);
        List<Musico> musicos = musicoRepository.findByCultoId(cultoId);
        List<Visitante> visitantes = visitanteRepository.findByCultoId(cultoId);
        List<Presbitero> presbiteros = presbiteroRepository.findByCultoId(cultoId);
        TesoureiroConferencia conferencia = tesoureiroConferenciaRepository.findByCultoId(cultoId).orElse(null);

        BigDecimal totalDizimos = dizimos.stream()
                .map(Dizimo::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOfertas = ofertas.stream()
                .map(Oferta::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CultoDashboardDTO.builder()
                .culto(culto)
                .louvores(louvores)
                .dizimos(dizimos)
                .ofertas(ofertas)
                .cooperadores(cooperadores)
                .musicos(musicos)
                .visitantes(visitantes)
                .presbiteros(presbiteros)
                .totalDizimos(totalDizimos)
                .totalOfertas(totalOfertas)
                .totalGeral(totalDizimos.add(totalOfertas))
                .totalPessoas(culto.getTotalPessoas() != null ? culto.getTotalPessoas() : 0)
                .conferencia(conferencia)
                .build();
    }

    /**
     * Registra um novo louvor no culto.
     *
     * @param louvor Entidade contendo nome do cantor e hino.
     * @return Louvor salvo.
     */
    @Transactional
    @CacheEvict(value = "dashboards", key = "#louvor.culto.id")
    public Louvor addLouvor(Louvor louvor) {
        Louvor saved = louvorRepository.save(louvor);
        eventPublisher.publishEvent(new br.com.igreja.ipiranga.modules.culto.domain.event.ItemCultoAdicionado("LOUVOR", saved.getId(), saved.getCulto().getId()));
        return saved;
    }

    @Transactional
    @CacheEvict(value = "dashboards", key = "#dizimo.culto.id")
    public Dizimo addDizimo(Dizimo dizimo) {
        Dizimo saved = dizimoRepository.save(dizimo);
        eventPublisher.publishEvent(new br.com.igreja.ipiranga.modules.culto.domain.event.ItemCultoAdicionado("DIZIMO", saved.getId(), saved.getCulto().getId()));
        return saved;
    }

    @Transactional
    @CacheEvict(value = "dashboards", key = "#oferta.culto.id")
    public Oferta addOferta(Oferta oferta) {
        Oferta saved = ofertaRepository.save(oferta);
        eventPublisher.publishEvent(new br.com.igreja.ipiranga.modules.culto.domain.event.ItemCultoAdicionado("OFERTA", saved.getId(), saved.getCulto().getId()));
        return saved;
    }

    @Transactional
    @CacheEvict(value = "dashboards", key = "#cooperador.culto.id")
    public Cooperador addCooperador(Cooperador cooperador) {
        Cooperador saved = cooperadorRepository.save(cooperador);
        eventPublisher.publishEvent(new br.com.igreja.ipiranga.modules.culto.domain.event.ItemCultoAdicionado("COOPERADOR", saved.getId(), saved.getCulto().getId()));
        return saved;
    }

    @Transactional
    @CacheEvict(value = "dashboards", key = "#musico.culto.id")
    public Musico addMusico(Musico musico) {
        Musico saved = musicoRepository.save(musico);
        eventPublisher.publishEvent(new br.com.igreja.ipiranga.modules.culto.domain.event.ItemCultoAdicionado("MUSICO", saved.getId(), saved.getCulto().getId()));
        return saved;
    }

    @Transactional
    @CacheEvict(value = "dashboards", key = "#visitante.culto.id")
    public Visitante addVisitante(Visitante visitante) {
        Visitante saved = visitanteRepository.save(visitante);
        eventPublisher.publishEvent(new br.com.igreja.ipiranga.modules.culto.domain.event.ItemCultoAdicionado("VISITANTE", saved.getId(), saved.getCulto().getId()));
        return saved;
    }

    @Transactional
    @CacheEvict(value = "dashboards", key = "#presbitero.culto.id")
    public Presbitero addPresbitero(Presbitero presbitero) {
        Presbitero saved = presbiteroRepository.save(presbitero);
        eventPublisher.publishEvent(new br.com.igreja.ipiranga.modules.culto.domain.event.ItemCultoAdicionado("PRESBITERO", saved.getId(), saved.getCulto().getId()));
        return saved;
    }

    /**
     * Registra a conferência de tesouraria.
     * <p>
     * Regra de Negócio: O valor conferido pelo tesoureiro é comparado com a soma de Dízimos e Ofertas.
     * Se houver diferença, o registro é salvo com status DIVERGENTE e a diferença é calculada.
     * Se bater, o status é CONFERIDO.
     * </p>
     *
     * @param conferencia Objeto com o valor declarado pelo tesoureiro.
     * @return Conferência salva com o status processado.
     */
    @Transactional
    @CacheEvict(value = "dashboards", key = "#conferencia.culto.id")
    public TesoureiroConferencia addConferencia(TesoureiroConferencia conferencia) {
        // Validação de Regra de Negócio: Comparação de Totais
        BigDecimal totalSistema = calcularTotalSistema(conferencia.getCulto().getId());
        BigDecimal totalInformado = conferencia.getTotalConferido();
        
        BigDecimal diferenca = totalInformado.subtract(totalSistema);
        
        conferencia.setDiferenca(diferenca);
        
        if (diferenca.compareTo(BigDecimal.ZERO) == 0) {
            conferencia.setStatus(TesoureiroConferencia.StatusConferencia.CONFERIDO);
        } else {
            conferencia.setStatus(TesoureiroConferencia.StatusConferencia.DIVERGENTE);
            // Poderíamos disparar um alerta específico para divergência aqui
            // Mantemos o Kafka direto apenas para alerta de erro crítico/monitoramento, ou criamos outro evento.
            // Simplificação para DDD: Vamos remover o kafka send direto e assumir que o alerta financeiro
            // seria tratado por um Listener de "ConferenciaRegistrada" que checa o status.
            // Para manter compatibilidade com o solicitado, vou usar o eventPublisher também.
            // kafkaTemplate.send("financeiro-alerts", "DIVERGENCIA_DETECTADA: Culto " + conferencia.getCulto().getId() + " Diff: " + diferenca);
        }

        TesoureiroConferencia saved = tesoureiroConferenciaRepository.save(conferencia);
        eventPublisher.publishEvent(new br.com.igreja.ipiranga.modules.culto.domain.event.ItemCultoAdicionado("CONFERENCIA", saved.getId(), saved.getCulto().getId()));
        return saved;
    }
    
    private BigDecimal calcularTotalSistema(UUID cultoId) {
        BigDecimal totalDizimos = dizimoRepository.findByCultoId(cultoId).stream()
                .map(Dizimo::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOfertas = ofertaRepository.findByCultoId(cultoId).stream()
                .map(Oferta::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                
        return totalDizimos.add(totalOfertas);
    }
}
