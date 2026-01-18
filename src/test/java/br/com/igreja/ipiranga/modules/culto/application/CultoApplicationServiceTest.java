package br.com.igreja.ipiranga.modules.culto.application;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import br.com.igreja.ipiranga.modules.culto.domain.repository.CultoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CultoApplicationServiceTest {

    @Mock
    private CultoRepository repository;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private CultoApplicationService service;

    @Test
    void shouldFindById() {
        Culto culto = Culto.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(culto));

        Culto result = service.findById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldSaveAndPublishEvent() {
        Culto culto = Culto.builder().tema("Teste").build();
        Culto saved = Culto.builder().id(1L).tema("Teste").build();

        when(repository.save(any(Culto.class))).thenReturn(saved);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(CompletableFuture.completedFuture(null));

        Culto result = service.save(culto);

        assertEquals(1L, result.getId());
        verify(kafkaTemplate).send(eq("culto-updates"), eq(saved));
    }
}
