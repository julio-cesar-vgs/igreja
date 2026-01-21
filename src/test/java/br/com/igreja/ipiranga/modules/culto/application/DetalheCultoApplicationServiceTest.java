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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetalheCultoApplicationServiceTest {

    @Mock private LouvorRepository louvorRepository;
    @Mock private DizimoRepository dizimoRepository;
    @Mock private OfertaRepository ofertaRepository;
    @Mock private CooperadorRepository cooperadorRepository;
    @Mock private MusicoRepository musicoRepository;
    @Mock private VisitanteRepository visitanteRepository;
    @Mock private PresbiteroRepository presbiteroRepository;
    @Mock private TesoureiroConferenciaRepository tesoureiroConferenciaRepository;
    @Mock private CultoRepository cultoRepository;
    @Mock private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private DetalheCultoApplicationService service;

    @Test
    void shouldAddLouvor() {
        Louvor louvor = Louvor.builder().pessoaNome("Teste").build();
        Louvor saved = Louvor.builder().id(1L).pessoaNome("Teste").build();

        when(louvorRepository.save(any(Louvor.class))).thenReturn(saved);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(CompletableFuture.completedFuture(null));

        Louvor result = service.addLouvor(louvor);

        assertEquals(1L, result.getId());
        verify(kafkaTemplate).send("culto-updates", "LOUVOR_ADDED:1");
    }

    @Test
    void shouldAddDizimo() {
        Dizimo dizimo = Dizimo.builder().valor(BigDecimal.TEN).build();
        Dizimo saved = Dizimo.builder().id(1L).valor(BigDecimal.TEN).build();

        when(dizimoRepository.save(any(Dizimo.class))).thenReturn(saved);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(CompletableFuture.completedFuture(null));

        Dizimo result = service.addDizimo(dizimo);

        assertEquals(1L, result.getId());
        verify(kafkaTemplate).send("culto-updates", "DIZIMO_ADDED:1");
    }

    @Test
    void shouldAddOferta() {
        Oferta oferta = Oferta.builder().valor(BigDecimal.TEN).build();
        Oferta saved = Oferta.builder().id(1L).valor(BigDecimal.TEN).build();

        when(ofertaRepository.save(any(Oferta.class))).thenReturn(saved);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(CompletableFuture.completedFuture(null));

        Oferta result = service.addOferta(oferta);

        assertEquals(1L, result.getId());
        verify(kafkaTemplate).send("culto-updates", "OFERTA_ADDED:1");
    }

    @Test
    void shouldAddCooperador() {
        Cooperador cooperador = Cooperador.builder().nome("Teste").build();
        Cooperador saved = Cooperador.builder().id(1L).nome("Teste").build();

        when(cooperadorRepository.save(any(Cooperador.class))).thenReturn(saved);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(CompletableFuture.completedFuture(null));

        Cooperador result = service.addCooperador(cooperador);

        assertEquals(1L, result.getId());
        verify(kafkaTemplate).send("culto-updates", "COOPERADOR_ADDED:1");
    }

    @Test
    void shouldAddMusico() {
        Musico musico = Musico.builder().nome("Teste").build();
        Musico saved = Musico.builder().id(1L).nome("Teste").build();

        when(musicoRepository.save(any(Musico.class))).thenReturn(saved);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(CompletableFuture.completedFuture(null));

        Musico result = service.addMusico(musico);

        assertEquals(1L, result.getId());
        verify(kafkaTemplate).send("culto-updates", "MUSICO_ADDED:1");
    }

    @Test
    void shouldAddVisitante() {
        Visitante visitante = Visitante.builder().nome("Teste").build();
        Visitante saved = Visitante.builder().id(1L).nome("Teste").build();

        when(visitanteRepository.save(any(Visitante.class))).thenReturn(saved);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(CompletableFuture.completedFuture(null));

        Visitante result = service.addVisitante(visitante);

        assertEquals(1L, result.getId());
        verify(kafkaTemplate).send("culto-updates", "VISITANTE_ADDED:1");
    }

    @Test
    void shouldAddPresbitero() {
        Presbitero presbitero = Presbitero.builder().nome("Teste").build();
        Presbitero saved = Presbitero.builder().id(1L).nome("Teste").build();

        when(presbiteroRepository.save(any(Presbitero.class))).thenReturn(saved);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(CompletableFuture.completedFuture(null));

        Presbitero result = service.addPresbitero(presbitero);

        assertEquals(1L, result.getId());
        verify(kafkaTemplate).send("culto-updates", "PRESBITERO_ADDED:1");
    }

    @Test
    void shouldAddConferencia() {
        TesoureiroConferencia conferencia = TesoureiroConferencia.builder().build();
        TesoureiroConferencia saved = TesoureiroConferencia.builder().id(1L).build();

        when(tesoureiroConferenciaRepository.save(any(TesoureiroConferencia.class))).thenReturn(saved);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(CompletableFuture.completedFuture(null));

        TesoureiroConferencia result = service.addConferencia(conferencia);

        assertEquals(1L, result.getId());
        verify(kafkaTemplate).send("culto-updates", "CONFERENCIA_ADDED:1");
    }

    @Test
    void shouldGetDashboard() {
        Long cultoId = 1L;
        Culto culto = Culto.builder().id(cultoId).totalPessoas(100).build();
        List<Louvor> louvores = Collections.singletonList(Louvor.builder().build());
        List<Dizimo> dizimos = Collections.singletonList(Dizimo.builder().valor(BigDecimal.valueOf(100)).build());
        List<Oferta> ofertas = Collections.singletonList(Oferta.builder().valor(BigDecimal.valueOf(50)).build());
        List<Cooperador> cooperadores = Collections.singletonList(Cooperador.builder().build());
        List<Musico> musicos = Collections.singletonList(Musico.builder().build());
        List<Visitante> visitantes = Collections.singletonList(Visitante.builder().build());
        List<Presbitero> presbiteros = Collections.singletonList(Presbitero.builder().build());
        TesoureiroConferencia conferencia = TesoureiroConferencia.builder().build();

        when(cultoRepository.findById(cultoId)).thenReturn(Optional.of(culto));
        when(louvorRepository.findByCultoId(cultoId)).thenReturn(louvores);
        when(dizimoRepository.findByCultoId(cultoId)).thenReturn(dizimos);
        when(ofertaRepository.findByCultoId(cultoId)).thenReturn(ofertas);
        when(cooperadorRepository.findByCultoId(cultoId)).thenReturn(cooperadores);
        when(musicoRepository.findByCultoId(cultoId)).thenReturn(musicos);
        when(visitanteRepository.findByCultoId(cultoId)).thenReturn(visitantes);
        when(presbiteroRepository.findByCultoId(cultoId)).thenReturn(presbiteros);
        when(tesoureiroConferenciaRepository.findByCultoId(cultoId)).thenReturn(Optional.of(conferencia));

        CultoDashboardDTO dashboard = service.getDashboard(cultoId);

        assertNotNull(dashboard);
        assertEquals(culto, dashboard.getCulto());
        assertEquals(louvores, dashboard.getLouvores());
        assertEquals(dizimos, dashboard.getDizimos());
        assertEquals(ofertas, dashboard.getOfertas());
        assertEquals(cooperadores, dashboard.getCooperadores());
        assertEquals(musicos, dashboard.getMusicos());
        assertEquals(visitantes, dashboard.getVisitantes());
        assertEquals(presbiteros, dashboard.getPresbiteros());
        assertEquals(conferencia, dashboard.getConferencia());
        assertEquals(BigDecimal.valueOf(100), dashboard.getTotalDizimos());
        assertEquals(BigDecimal.valueOf(50), dashboard.getTotalOfertas());
        assertEquals(BigDecimal.valueOf(150), dashboard.getTotalGeral());
        assertEquals(100, dashboard.getTotalPessoas());
    }

    @Test
    void shouldGetDashboardWithNullTotalPessoas() {
        Long cultoId = 1L;
        Culto culto = Culto.builder().id(cultoId).totalPessoas(null).build();

        when(cultoRepository.findById(cultoId)).thenReturn(Optional.of(culto));
        when(louvorRepository.findByCultoId(cultoId)).thenReturn(Collections.emptyList());
        when(dizimoRepository.findByCultoId(cultoId)).thenReturn(Collections.emptyList());
        when(ofertaRepository.findByCultoId(cultoId)).thenReturn(Collections.emptyList());
        when(cooperadorRepository.findByCultoId(cultoId)).thenReturn(Collections.emptyList());
        when(musicoRepository.findByCultoId(cultoId)).thenReturn(Collections.emptyList());
        when(visitanteRepository.findByCultoId(cultoId)).thenReturn(Collections.emptyList());
        when(presbiteroRepository.findByCultoId(cultoId)).thenReturn(Collections.emptyList());
        when(tesoureiroConferenciaRepository.findByCultoId(cultoId)).thenReturn(Optional.empty());

        CultoDashboardDTO dashboard = service.getDashboard(cultoId);

        assertNotNull(dashboard);
        assertEquals(0, dashboard.getTotalPessoas());
        assertNull(dashboard.getConferencia());
    }

    @Test
    void shouldThrowExceptionWhenCultoNotFound() {
        Long cultoId = 1L;
        when(cultoRepository.findById(cultoId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.getDashboard(cultoId));
        assertEquals("Culto não encontrado", exception.getMessage());
    }
}
