package br.com.igreja.ipiranga.modules.culto.application;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import br.com.igreja.ipiranga.modules.culto.domain.repository.CultoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CultoApplicationServiceTest {

    @Mock
    private CultoRepository repository;

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

        Culto result = service.save(culto);

        assertEquals(1L, result.getId());
        verify(repository).save(culto);
        
        // Verify that the domain event was registered
        assertFalse(culto.domainEvents().isEmpty(), "Domain event should be registered");
    }
}
