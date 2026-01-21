package br.com.igreja.ipiranga.modules.igreja.web;

import br.com.igreja.ipiranga.infrastructure.security.JwtService;
import br.com.igreja.ipiranga.modules.igreja.application.IgrejaApplicationService;
import br.com.igreja.ipiranga.modules.igreja.domain.model.Igreja;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IgrejaController.class)
@AutoConfigureMockMvc(addFilters = false)
class IgrejaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IgrejaApplicationService service;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllIgrejas() throws Exception {
        Igreja igreja1 = Igreja.builder().id(1L).nome("Matriz").tipo(Igreja.TipoIgreja.MATRIZ).build();
        Igreja igreja2 = Igreja.builder().id(2L).nome("Filial 1").tipo(Igreja.TipoIgreja.FILIAL).build();
        List<Igreja> igrejas = Arrays.asList(igreja1, igreja2);

        when(service.findAll()).thenReturn(igrejas);

        mockMvc.perform(get("/api/igrejas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Matriz"));
    }

    @Test
    void shouldReturnIgrejaById() throws Exception {
        Igreja igreja = Igreja.builder().id(1L).nome("Matriz").tipo(Igreja.TipoIgreja.MATRIZ).build();

        when(service.findById(1L)).thenReturn(igreja);

        mockMvc.perform(get("/api/igrejas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Matriz"));
    }

    @Test
    void shouldCreateIgreja() throws Exception {
        Igreja igreja = Igreja.builder().nome("Nova Filial").tipo(Igreja.TipoIgreja.FILIAL).build();
        Igreja savedIgreja = Igreja.builder().id(3L).nome("Nova Filial").tipo(Igreja.TipoIgreja.FILIAL).build();

        when(service.save(any(Igreja.class))).thenReturn(savedIgreja);

        mockMvc.perform(post("/api/igrejas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(igreja)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nome").value("Nova Filial"));
    }

    @Test
    void shouldUpdateIgreja() throws Exception {
        Igreja igreja = Igreja.builder().nome("Filial Updated").tipo(Igreja.TipoIgreja.FILIAL).build();
        Igreja updatedIgreja = Igreja.builder().id(2L).nome("Filial Updated").tipo(Igreja.TipoIgreja.FILIAL).build();

        when(service.save(any(Igreja.class))).thenReturn(updatedIgreja);

        mockMvc.perform(put("/api/igrejas/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(igreja)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nome").value("Filial Updated"));
    }

    @Test
    void shouldDeleteIgreja() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/igrejas/1"))
                .andExpect(status().isNoContent());
    }
}
