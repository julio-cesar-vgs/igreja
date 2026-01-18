package br.com.igreja.ipiranga.modules.culto.application;

import br.com.igreja.ipiranga.modules.culto.domain.model.Louvor;
import br.com.igreja.ipiranga.modules.culto.domain.repository.*;
import br.com.igreja.ipiranga.modules.financeiro.domain.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
