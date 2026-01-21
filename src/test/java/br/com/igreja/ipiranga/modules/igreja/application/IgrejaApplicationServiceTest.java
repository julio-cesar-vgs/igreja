package br.com.igreja.ipiranga.modules.igreja.application;

import br.com.igreja.ipiranga.modules.igreja.domain.model.Igreja;
import br.com.igreja.ipiranga.modules.igreja.domain.repository.IgrejaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IgrejaApplicationServiceTest {

    @Mock
    private IgrejaRepository repository;

    @InjectMocks
    private IgrejaApplicationService service;

    @Test
    void shouldFindAll() {
        Igreja igreja1 = Igreja.builder().id(UUID.randomUUID()).build();
        when(repository.findAll()).thenReturn(Arrays.asList(igreja1));

        List<Igreja> result = service.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldFindById() {
        UUID id = UUID.randomUUID();
        Igreja igreja = Igreja.builder().id(id).build();
        when(repository.findById(id)).thenReturn(Optional.of(igreja));

        Igreja result = service.findById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldSave() {
        UUID id = UUID.randomUUID();
        Igreja igreja = Igreja.builder().nome("Nova").build();
        Igreja saved = Igreja.builder().id(id).nome("Nova").build();
        when(repository.save(any(Igreja.class))).thenReturn(saved);

        Igreja result = service.save(igreja);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldDelete() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(id);
        service.delete(id);
        verify(repository, times(1)).deleteById(id);
    }
}
