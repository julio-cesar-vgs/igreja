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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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
    private final KafkaTemplate<String, Object> kafkaTemplate;

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
    public CultoDashboardDTO getDashboard(Long cultoId) {
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
    public Louvor addLouvor(Louvor louvor) {
        Louvor saved = louvorRepository.save(louvor);
        kafkaTemplate.send("culto-updates", "LOUVOR_ADDED:" + saved.getId());
        return saved;
    }

    @Transactional
    public Dizimo addDizimo(Dizimo dizimo) {
        Dizimo saved = dizimoRepository.save(dizimo);
        kafkaTemplate.send("culto-updates", "DIZIMO_ADDED:" + saved.getId());
        return saved;
    }

    @Transactional
    public Oferta addOferta(Oferta oferta) {
        Oferta saved = ofertaRepository.save(oferta);
        kafkaTemplate.send("culto-updates", "OFERTA_ADDED:" + saved.getId());
        return saved;
    }

    @Transactional
    public Cooperador addCooperador(Cooperador cooperador) {
        Cooperador saved = cooperadorRepository.save(cooperador);
        kafkaTemplate.send("culto-updates", "COOPERADOR_ADDED:" + saved.getId());
        return saved;
    }

    @Transactional
    public Musico addMusico(Musico musico) {
        Musico saved = musicoRepository.save(musico);
        kafkaTemplate.send("culto-updates", "MUSICO_ADDED:" + saved.getId());
        return saved;
    }

    @Transactional
    public Visitante addVisitante(Visitante visitante) {
        Visitante saved = visitanteRepository.save(visitante);
        kafkaTemplate.send("culto-updates", "VISITANTE_ADDED:" + saved.getId());
        return saved;
    }

    @Transactional
    public Presbitero addPresbitero(Presbitero presbitero) {
        Presbitero saved = presbiteroRepository.save(presbitero);
        kafkaTemplate.send("culto-updates", "PRESBITERO_ADDED:" + saved.getId());
        return saved;
    }

    @Transactional
    public TesoureiroConferencia addConferencia(TesoureiroConferencia conferencia) {
        TesoureiroConferencia saved = tesoureiroConferenciaRepository.save(conferencia);
        kafkaTemplate.send("culto-updates", "CONFERENCIA_ADDED:" + saved.getId());
        return saved;
    }
}
