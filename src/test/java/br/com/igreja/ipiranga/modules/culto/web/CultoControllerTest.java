package br.com.igreja.ipiranga.modules.culto.web;

import br.com.igreja.ipiranga.infrastructure.security.JwtService;
import br.com.igreja.ipiranga.modules.culto.application.CultoApplicationService;
import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CultoController.class)
@AutoConfigureMockMvc(addFilters = false)
class CultoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CultoApplicationService service;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllCultos() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/cultos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldCreateCulto() throws Exception {
        Culto culto = Culto.builder().tema("Culto da Vitória").build();
        Culto saved = Culto.builder().id(1L).tema("Culto da Vitória").build();

        when(service.save(any(Culto.class))).thenReturn(saved);

        mockMvc.perform(post("/api/cultos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(culto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tema").value("Culto da Vitória"));
    }
}
