package br.com.igreja.ipiranga.modules.igreja.application;

import br.com.igreja.ipiranga.modules.igreja.domain.model.Igreja;
import br.com.igreja.ipiranga.modules.igreja.domain.repository.IgrejaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Igreja igreja1 = Igreja.builder().id(1L).build();
        when(repository.findAll()).thenReturn(Arrays.asList(igreja1));

        List<Igreja> result = service.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldFindById() {
        Igreja igreja = Igreja.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(igreja));

        Igreja result = service.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldSave() {
        Igreja igreja = Igreja.builder().nome("Nova").build();
        Igreja saved = Igreja.builder().id(1L).nome("Nova").build();
        when(repository.save(any(Igreja.class))).thenReturn(saved);

        Igreja result = service.save(igreja);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldDelete() {
        doNothing().when(repository).deleteById(1L);
        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}
