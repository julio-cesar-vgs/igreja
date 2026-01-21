package br.com.igreja.ipiranga.modules.financeiro.domain.model;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TesoureiroConferenciaTest {

    @Test
    void testNoArgsConstructor() {
        // When
        TesoureiroConferencia conferencia = new TesoureiroConferencia();

        // Then
        assertThat(conferencia).isNotNull();
        assertThat(conferencia.getId()).isNull();
        assertThat(conferencia.getCulto()).isNull();
        assertThat(conferencia.getTesoureiroId()).isNull();
        assertThat(conferencia.getTotalConferido()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        Long tesoureiroId = 5L;
        BigDecimal totalConferido = BigDecimal.valueOf(1000.00);
        TesoureiroConferencia.StatusConferencia status = TesoureiroConferencia.StatusConferencia.CONFERIDO;
        BigDecimal diferenca = BigDecimal.ZERO;

        // When
        TesoureiroConferencia conferencia = new TesoureiroConferencia(id, culto, tesoureiroId, totalConferido, status, diferenca);

        // Then
        assertThat(conferencia).isNotNull();
        assertThat(conferencia.getId()).isEqualTo(id);
        assertThat(conferencia.getCulto()).isEqualTo(culto);
        assertThat(conferencia.getTesoureiroId()).isEqualTo(tesoureiroId);
        assertThat(conferencia.getTotalConferido()).isEqualByComparingTo(totalConferido);
        assertThat(conferencia.getStatus()).isEqualTo(status);
        assertThat(conferencia.getDiferenca()).isEqualTo(diferenca);
    }

    @Test
    void testBuilder() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        Long tesoureiroId = 5L;
        BigDecimal totalConferido = BigDecimal.valueOf(1500.50);

        // When
        TesoureiroConferencia conferencia = TesoureiroConferencia.builder()
                .id(id)
                .culto(culto)
                .tesoureiroId(tesoureiroId)
                .totalConferido(totalConferido)
                .build();

        // Then
        assertThat(conferencia).isNotNull();
        assertThat(conferencia.getId()).isEqualTo(id);
        assertThat(conferencia.getCulto()).isEqualTo(culto);
        assertThat(conferencia.getTesoureiroId()).isEqualTo(tesoureiroId);
        assertThat(conferencia.getTotalConferido()).isEqualByComparingTo(totalConferido);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        TesoureiroConferencia conferencia = new TesoureiroConferencia();
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        Long tesoureiroId = 7L;
        BigDecimal totalConferido = BigDecimal.valueOf(2000.75);

        // When
        conferencia.setId(id);
        conferencia.setCulto(culto);
        conferencia.setTesoureiroId(tesoureiroId);
        conferencia.setTotalConferido(totalConferido);

        // Then
        assertThat(conferencia.getId()).isEqualTo(id);
        assertThat(conferencia.getCulto()).isEqualTo(culto);
        assertThat(conferencia.getTesoureiroId()).isEqualTo(tesoureiroId);
        assertThat(conferencia.getTotalConferido()).isEqualByComparingTo(totalConferido);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        TesoureiroConferencia conferencia1 = TesoureiroConferencia.builder()
                .id(1L)
                .culto(culto)
                .tesoureiroId(5L)
                .totalConferido(BigDecimal.valueOf(1000.00))
                .build();

        TesoureiroConferencia conferencia2 = TesoureiroConferencia.builder()
                .id(1L)
                .culto(culto)
                .tesoureiroId(5L)
                .totalConferido(BigDecimal.valueOf(1000.00))
                .build();

        TesoureiroConferencia conferencia3 = TesoureiroConferencia.builder()
                .id(2L)
                .culto(culto)
                .tesoureiroId(6L)
                .totalConferido(BigDecimal.valueOf(2000.00))
                .build();

        // Then
        assertThat(conferencia1).isEqualTo(conferencia2);
        assertThat(conferencia1).hasSameHashCodeAs(conferencia2);
        assertThat(conferencia1).isNotEqualTo(conferencia3);
        assertThat(conferencia1.hashCode()).isNotEqualTo(conferencia3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        TesoureiroConferencia conferencia = TesoureiroConferencia.builder()
                .id(1L)
                .culto(culto)
                .tesoureiroId(5L)
                .totalConferido(BigDecimal.valueOf(1000.00))
                .build();

        // When
        String toString = conferencia.toString();

        // Then
        assertThat(toString).contains("TesoureiroConferencia");
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("tesoureiroId=5");
        assertThat(toString).contains("totalConferido=1000");
    }

    @Test
    void testBuilderWithNullTesoureiroId() {
        // Given
        Culto culto = Culto.builder().id(10L).build();

        // When
        TesoureiroConferencia conferencia = TesoureiroConferencia.builder()
                .id(1L)
                .culto(culto)
                .tesoureiroId(null)
                .totalConferido(BigDecimal.valueOf(1000.00))
                .build();

        // Then
        assertThat(conferencia.getTesoureiroId()).isNull();
    }

    @Test
    void testBuilderWithNullTotalConferido() {
        // Given
        Culto culto = Culto.builder().id(10L).build();

        // When
        TesoureiroConferencia conferencia = TesoureiroConferencia.builder()
                .id(1L)
                .culto(culto)
                .tesoureiroId(5L)
                .totalConferido(null)
                .build();

        // Then
        assertThat(conferencia.getTotalConferido()).isNull();
    }

    @Test
    void testBuilderWithZeroTotalConferido() {
        // Given
        Culto culto = Culto.builder().id(10L).build();

        // When
        TesoureiroConferencia conferencia = TesoureiroConferencia.builder()
                .id(1L)
                .culto(culto)
                .tesoureiroId(5L)
                .totalConferido(BigDecimal.ZERO)
                .build();

        // Then
        assertThat(conferencia.getTotalConferido()).isEqualByComparingTo(BigDecimal.ZERO);
    }
}
