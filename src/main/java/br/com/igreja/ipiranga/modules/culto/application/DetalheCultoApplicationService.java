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
 * Service de Aplicação: DetalheCultoApplicationService
 * Camada: Application
 * 
 * Orquestra a adição de itens ao culto e a geração do dashboard.
 * Cruza informações dos contextos de Culto e Financeiro para fornecer uma visão unificada.
 * 
 * Responsabilidades:
 * - Adicionar detalhes ao culto (Louvores, Músicos, etc.).
 * - Registrar entradas financeiras (Dízimos, Ofertas) associadas ao culto.
 * - Gerar o DTO de Dashboard consolidando todas as informações.
 * - Publicar eventos de atualização via Kafka para notificações em tempo real.
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
     * Consolida todas as informações de um culto em um único DTO.
     * Realiza múltiplas consultas aos repositórios e cálculos de totais financeiros.
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
