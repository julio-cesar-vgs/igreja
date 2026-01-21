package br.com.igreja.ipiranga.modules.culto.application.dto;

import br.com.igreja.ipiranga.modules.culto.domain.model.*;
import br.com.igreja.ipiranga.modules.financeiro.domain.model.Dizimo;
import br.com.igreja.ipiranga.modules.financeiro.domain.model.Oferta;
import br.com.igreja.ipiranga.modules.financeiro.domain.model.TesoureiroConferencia;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CultoDashboardDTOTest {

    @Test
    void testBuilder() {
        // Given
        Culto culto = Culto.builder()
                .id(1L)
                .dataHora(LocalDateTime.now())
                .tema("Tema do Culto")
                .status(Culto.StatusCulto.EM_ANDAMENTO)
                .build();

        List<Louvor> louvores = Arrays.asList(
                Louvor.builder().id(1L).pessoaNome("João").build(),
                Louvor.builder().id(2L).pessoaNome("Maria").build()
        );

        List<Cooperador> cooperadores = Arrays.asList(
                Cooperador.builder().id(1L).nome("Pedro").build()
        );

        List<Musico> musicos = Arrays.asList(
                Musico.builder().id(1L).nome("Ana").build()
        );

        List<Presbitero> presbiteros = Arrays.asList(
                Presbitero.builder().id(1L).nome("Carlos").build()
        );

        List<Visitante> visitantes = Arrays.asList(
                Visitante.builder().id(1L).nome("Visitante 1").build()
        );

        List<Dizimo> dizimos = Arrays.asList(
                Dizimo.builder().id(1L).valor(BigDecimal.valueOf(100)).build()
        );

        List<Oferta> ofertas = Arrays.asList(
                Oferta.builder().id(1L).valor(BigDecimal.valueOf(50)).build()
        );

        BigDecimal totalDizimos = BigDecimal.valueOf(100);
        BigDecimal totalOfertas = BigDecimal.valueOf(50);
        BigDecimal totalGeral = BigDecimal.valueOf(150);
        int totalPessoas = 50;

        TesoureiroConferencia conferencia = TesoureiroConferencia.builder()
                .id(1L)
                .totalConferido(totalGeral)
                .build();

        // When
        CultoDashboardDTO dto = CultoDashboardDTO.builder()
                .culto(culto)
                .louvores(louvores)
                .cooperadores(cooperadores)
                .musicos(musicos)
                .presbiteros(presbiteros)
                .visitantes(visitantes)
                .dizimos(dizimos)
                .ofertas(ofertas)
                .totalDizimos(totalDizimos)
                .totalOfertas(totalOfertas)
                .totalGeral(totalGeral)
                .totalPessoas(totalPessoas)
                .conferencia(conferencia)
                .build();

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getCulto()).isEqualTo(culto);
        assertThat(dto.getLouvores()).hasSize(2).containsExactlyElementsOf(louvores);
        assertThat(dto.getCooperadores()).hasSize(1).containsExactlyElementsOf(cooperadores);
        assertThat(dto.getMusicos()).hasSize(1).containsExactlyElementsOf(musicos);
        assertThat(dto.getPresbiteros()).hasSize(1).containsExactlyElementsOf(presbiteros);
        assertThat(dto.getVisitantes()).hasSize(1).containsExactlyElementsOf(visitantes);
        assertThat(dto.getDizimos()).hasSize(1).containsExactlyElementsOf(dizimos);
        assertThat(dto.getOfertas()).hasSize(1).containsExactlyElementsOf(ofertas);
        assertThat(dto.getTotalDizimos()).isEqualByComparingTo(totalDizimos);
        assertThat(dto.getTotalOfertas()).isEqualByComparingTo(totalOfertas);
        assertThat(dto.getTotalGeral()).isEqualByComparingTo(totalGeral);
        assertThat(dto.getTotalPessoas()).isEqualTo(totalPessoas);
        assertThat(dto.getConferencia()).isEqualTo(conferencia);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        CultoDashboardDTO dto = CultoDashboardDTO.builder().build();

        Culto culto = Culto.builder().id(1L).build();
        List<Louvor> louvores = Arrays.asList(Louvor.builder().id(1L).build());
        List<Cooperador> cooperadores = Arrays.asList(Cooperador.builder().id(1L).build());
        List<Musico> musicos = Arrays.asList(Musico.builder().id(1L).build());
        List<Presbitero> presbiteros = Arrays.asList(Presbitero.builder().id(1L).build());
        List<Visitante> visitantes = Arrays.asList(Visitante.builder().id(1L).build());
        List<Dizimo> dizimos = Arrays.asList(Dizimo.builder().id(1L).build());
        List<Oferta> ofertas = Arrays.asList(Oferta.builder().id(1L).build());
        BigDecimal totalDizimos = BigDecimal.valueOf(200);
        BigDecimal totalOfertas = BigDecimal.valueOf(100);
        BigDecimal totalGeral = BigDecimal.valueOf(300);
        int totalPessoas = 75;
        TesoureiroConferencia conferencia = TesoureiroConferencia.builder().id(1L).build();

        // When
        dto.setCulto(culto);
        dto.setLouvores(louvores);
        dto.setCooperadores(cooperadores);
        dto.setMusicos(musicos);
        dto.setPresbiteros(presbiteros);
        dto.setVisitantes(visitantes);
        dto.setDizimos(dizimos);
        dto.setOfertas(ofertas);
        dto.setTotalDizimos(totalDizimos);
        dto.setTotalOfertas(totalOfertas);
        dto.setTotalGeral(totalGeral);
        dto.setTotalPessoas(totalPessoas);
        dto.setConferencia(conferencia);

        // Then
        assertThat(dto.getCulto()).isEqualTo(culto);
        assertThat(dto.getLouvores()).isEqualTo(louvores);
        assertThat(dto.getCooperadores()).isEqualTo(cooperadores);
        assertThat(dto.getMusicos()).isEqualTo(musicos);
        assertThat(dto.getPresbiteros()).isEqualTo(presbiteros);
        assertThat(dto.getVisitantes()).isEqualTo(visitantes);
        assertThat(dto.getDizimos()).isEqualTo(dizimos);
        assertThat(dto.getOfertas()).isEqualTo(ofertas);
        assertThat(dto.getTotalDizimos()).isEqualByComparingTo(totalDizimos);
        assertThat(dto.getTotalOfertas()).isEqualByComparingTo(totalOfertas);
        assertThat(dto.getTotalGeral()).isEqualByComparingTo(totalGeral);
        assertThat(dto.getTotalPessoas()).isEqualTo(totalPessoas);
        assertThat(dto.getConferencia()).isEqualTo(conferencia);
    }

    @Test
    void testBuilderWithEmptyLists() {
        // When
        CultoDashboardDTO dto = CultoDashboardDTO.builder()
                .louvores(Arrays.asList())
                .cooperadores(Arrays.asList())
                .musicos(Arrays.asList())
                .presbiteros(Arrays.asList())
                .visitantes(Arrays.asList())
                .dizimos(Arrays.asList())
                .ofertas(Arrays.asList())
                .totalDizimos(BigDecimal.ZERO)
                .totalOfertas(BigDecimal.ZERO)
                .totalGeral(BigDecimal.ZERO)
                .totalPessoas(0)
                .build();

        // Then
        assertThat(dto.getLouvores()).isEmpty();
        assertThat(dto.getCooperadores()).isEmpty();
        assertThat(dto.getMusicos()).isEmpty();
        assertThat(dto.getPresbiteros()).isEmpty();
        assertThat(dto.getVisitantes()).isEmpty();
        assertThat(dto.getDizimos()).isEmpty();
        assertThat(dto.getOfertas()).isEmpty();
        assertThat(dto.getTotalDizimos()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(dto.getTotalOfertas()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(dto.getTotalGeral()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(dto.getTotalPessoas()).isEqualTo(0);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Culto culto = Culto.builder().id(1L).build();
        CultoDashboardDTO dto1 = CultoDashboardDTO.builder()
                .culto(culto)
                .totalPessoas(100)
                .totalGeral(BigDecimal.valueOf(500))
                .build();

        CultoDashboardDTO dto2 = CultoDashboardDTO.builder()
                .culto(culto)
                .totalPessoas(100)
                .totalGeral(BigDecimal.valueOf(500))
                .build();

        CultoDashboardDTO dto3 = CultoDashboardDTO.builder()
                .culto(Culto.builder().id(2L).build())
                .totalPessoas(200)
                .totalGeral(BigDecimal.valueOf(1000))
                .build();

        // Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1).hasSameHashCodeAs(dto2);
        assertThat(dto1).isNotEqualTo(dto3);
    }

    @Test
    void testToString() {
        // Given
        CultoDashboardDTO dto = CultoDashboardDTO.builder()
                .totalPessoas(50)
                .totalGeral(BigDecimal.valueOf(150))
                .build();

        // When
        String toString = dto.toString();

        // Then
        assertThat(toString).contains("CultoDashboardDTO");
        assertThat(toString).contains("totalPessoas=50");
    }
}
