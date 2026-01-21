package br.com.igreja.ipiranga.modules.culto.web;

import br.com.igreja.ipiranga.infrastructure.security.JwtService;
import br.com.igreja.ipiranga.modules.culto.application.DetalheCultoApplicationService;
import br.com.igreja.ipiranga.modules.culto.application.dto.CultoDashboardDTO;
import br.com.igreja.ipiranga.modules.culto.domain.model.Louvor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DetalheCultoController.class)
@AutoConfigureMockMvc(addFilters = false)
class DetalheCultoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DetalheCultoApplicationService service;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetDashboard() throws Exception {
        CultoDashboardDTO dashboard = CultoDashboardDTO.builder().build();
        when(service.getDashboard(1L)).thenReturn(dashboard);

        mockMvc.perform(get("/api/cultos/1/dashboard"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddLouvor() throws Exception {
        Louvor louvor = Louvor.builder().pessoaNome("Ressuscita-me").build();
        Louvor saved = Louvor.builder().id(10L).pessoaNome("Ressuscita-me").build();

        when(service.addLouvor(any(Louvor.class))).thenReturn(saved);

        mockMvc.perform(post("/api/cultos/1/louvores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(louvor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));
    }
}
