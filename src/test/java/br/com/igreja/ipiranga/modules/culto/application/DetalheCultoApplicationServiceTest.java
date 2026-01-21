package br.com.igreja.ipiranga.modules.culto.application;

import br.com.igreja.ipiranga.modules.culto.application.dto.CultoDashboardDTO;
import br.com.igreja.ipiranga.modules.culto.domain.event.ItemCultoAdicionado;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    @Mock private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private DetalheCultoApplicationService service;

    @Test
    void shouldAddLouvor() {
        Culto culto = Culto.builder().id(1L).build();
        Louvor louvor = Louvor.builder().pessoaNome("Teste").culto(culto).build();
        Louvor saved = Louvor.builder().id(1L).pessoaNome("Teste").culto(culto).build();

        when(louvorRepository.save(any(Louvor.class))).thenReturn(saved);

        Louvor result = service.addLouvor(louvor);

        assertEquals(1L, result.getId());
        
        ArgumentCaptor<ItemCultoAdicionado> eventCaptor = ArgumentCaptor.forClass(ItemCultoAdicionado.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        assertEquals("LOUVOR", eventCaptor.getValue().getTipoItem());
        assertEquals(1L, eventCaptor.getValue().getItemId());
    }

    @Test
    void shouldAddDizimo() {
        Culto culto = Culto.builder().id(1L).build();
        Dizimo dizimo = Dizimo.builder().valor(BigDecimal.TEN).culto(culto).build();
        Dizimo saved = Dizimo.builder().id(1L).valor(BigDecimal.TEN).culto(culto).build();

        when(dizimoRepository.save(any(Dizimo.class))).thenReturn(saved);

        Dizimo result = service.addDizimo(dizimo);

        assertEquals(1L, result.getId());
        
        ArgumentCaptor<ItemCultoAdicionado> eventCaptor = ArgumentCaptor.forClass(ItemCultoAdicionado.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        assertEquals("DIZIMO", eventCaptor.getValue().getTipoItem());
    }

    @Test
    void shouldAddOferta() {
        Culto culto = Culto.builder().id(1L).build();
        Oferta oferta = Oferta.builder().valor(BigDecimal.TEN).culto(culto).build();
        Oferta saved = Oferta.builder().id(1L).valor(BigDecimal.TEN).culto(culto).build();

        when(ofertaRepository.save(any(Oferta.class))).thenReturn(saved);

        Oferta result = service.addOferta(oferta);

        assertEquals(1L, result.getId());
        
        ArgumentCaptor<ItemCultoAdicionado> eventCaptor = ArgumentCaptor.forClass(ItemCultoAdicionado.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        assertEquals("OFERTA", eventCaptor.getValue().getTipoItem());
    }

    @Test
    void shouldAddCooperador() {
        Culto culto = Culto.builder().id(1L).build();
        Cooperador cooperador = Cooperador.builder().nome("Teste").culto(culto).build();
        Cooperador saved = Cooperador.builder().id(1L).nome("Teste").culto(culto).build();

        when(cooperadorRepository.save(any(Cooperador.class))).thenReturn(saved);

        Cooperador result = service.addCooperador(cooperador);

        assertEquals(1L, result.getId());
        
        ArgumentCaptor<ItemCultoAdicionado> eventCaptor = ArgumentCaptor.forClass(ItemCultoAdicionado.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        assertEquals("COOPERADOR", eventCaptor.getValue().getTipoItem());
    }

    @Test
    void shouldAddMusico() {
        Culto culto = Culto.builder().id(1L).build();
        Musico musico = Musico.builder().nome("Teste").culto(culto).build();
        Musico saved = Musico.builder().id(1L).nome("Teste").culto(culto).build();

        when(musicoRepository.save(any(Musico.class))).thenReturn(saved);

        Musico result = service.addMusico(musico);

        assertEquals(1L, result.getId());
        
        ArgumentCaptor<ItemCultoAdicionado> eventCaptor = ArgumentCaptor.forClass(ItemCultoAdicionado.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        assertEquals("MUSICO", eventCaptor.getValue().getTipoItem());
    }

    @Test
    void shouldAddVisitante() {
        Culto culto = Culto.builder().id(1L).build();
        Visitante visitante = Visitante.builder().nome("Teste").culto(culto).build();
        Visitante saved = Visitante.builder().id(1L).nome("Teste").culto(culto).build();

        when(visitanteRepository.save(any(Visitante.class))).thenReturn(saved);

        Visitante result = service.addVisitante(visitante);

        assertEquals(1L, result.getId());
        
        ArgumentCaptor<ItemCultoAdicionado> eventCaptor = ArgumentCaptor.forClass(ItemCultoAdicionado.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        assertEquals("VISITANTE", eventCaptor.getValue().getTipoItem());
    }

    @Test
    void shouldAddPresbitero() {
        Culto culto = Culto.builder().id(1L).build();
        Presbitero presbitero = Presbitero.builder().nome("Teste").culto(culto).build();
        Presbitero saved = Presbitero.builder().id(1L).nome("Teste").culto(culto).build();

        when(presbiteroRepository.save(any(Presbitero.class))).thenReturn(saved);

        Presbitero result = service.addPresbitero(presbitero);

        assertEquals(1L, result.getId());
        
        ArgumentCaptor<ItemCultoAdicionado> eventCaptor = ArgumentCaptor.forClass(ItemCultoAdicionado.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        assertEquals("PRESBITERO", eventCaptor.getValue().getTipoItem());
    }

    @Test
    void shouldAddConferencia() {
        Culto culto = Culto.builder().id(1L).build();
        TesoureiroConferencia conferencia = TesoureiroConferencia.builder().culto(culto).totalConferido(BigDecimal.ZERO).build();
        TesoureiroConferencia saved = TesoureiroConferencia.builder().id(1L).culto(culto).totalConferido(BigDecimal.ZERO).build();

        when(dizimoRepository.findByCultoId(1L)).thenReturn(Collections.emptyList());
        when(ofertaRepository.findByCultoId(1L)).thenReturn(Collections.emptyList());
        when(tesoureiroConferenciaRepository.save(any(TesoureiroConferencia.class))).thenReturn(saved);

        TesoureiroConferencia result = service.addConferencia(conferencia);

        assertEquals(1L, result.getId());
        
        ArgumentCaptor<ItemCultoAdicionado> eventCaptor = ArgumentCaptor.forClass(ItemCultoAdicionado.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        assertEquals("CONFERENCIA", eventCaptor.getValue().getTipoItem());
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
