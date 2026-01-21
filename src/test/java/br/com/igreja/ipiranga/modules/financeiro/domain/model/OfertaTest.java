package br.com.igreja.ipiranga.modules.financeiro.domain.model;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OfertaTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Oferta oferta = new Oferta();

        // Then
        assertThat(oferta).isNotNull();
        assertThat(oferta.getId()).isNull();
        assertThat(oferta.getCulto()).isNull();
        assertThat(oferta.getValor()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        BigDecimal valor = BigDecimal.valueOf(50.00);

        // When
        Oferta oferta = new Oferta(id, culto, valor);

        // Then
        assertThat(oferta).isNotNull();
        assertThat(oferta.getId()).isEqualTo(id);
        assertThat(oferta.getCulto()).isEqualTo(culto);
        assertThat(oferta.getValor()).isEqualByComparingTo(valor);
    }

    @Test
    void testBuilder() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        BigDecimal valor = BigDecimal.valueOf(75.50);

        // When
        Oferta oferta = Oferta.builder()
                .id(id)
                .culto(culto)
                .valor(valor)
                .build();

        // Then
        assertThat(oferta).isNotNull();
        assertThat(oferta.getId()).isEqualTo(id);
        assertThat(oferta.getCulto()).isEqualTo(culto);
        assertThat(oferta.getValor()).isEqualByComparingTo(valor);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Oferta oferta = new Oferta();
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        BigDecimal valor = BigDecimal.valueOf(100.25);

        // When
        oferta.setId(id);
        oferta.setCulto(culto);
        oferta.setValor(valor);

        // Then
        assertThat(oferta.getId()).isEqualTo(id);
        assertThat(oferta.getCulto()).isEqualTo(culto);
        assertThat(oferta.getValor()).isEqualByComparingTo(valor);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        Oferta oferta1 = Oferta.builder()
                .id(1L)
                .culto(culto)
                .valor(BigDecimal.valueOf(50.00))
                .build();

        Oferta oferta2 = Oferta.builder()
                .id(1L)
                .culto(culto)
                .valor(BigDecimal.valueOf(50.00))
                .build();

        Oferta oferta3 = Oferta.builder()
                .id(2L)
                .culto(culto)
                .valor(BigDecimal.valueOf(100.00))
                .build();

        // Then
        assertThat(oferta1).isEqualTo(oferta2);
        assertThat(oferta1).hasSameHashCodeAs(oferta2);
        assertThat(oferta1).isNotEqualTo(oferta3);
        assertThat(oferta1.hashCode()).isNotEqualTo(oferta3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        Oferta oferta = Oferta.builder()
                .id(1L)
                .culto(culto)
                .valor(BigDecimal.valueOf(50.00))
                .build();

        // When
        String toString = oferta.toString();

        // Then
        assertThat(toString).contains("Oferta");
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("valor=50");
    }

    @Test
    void testBuilderWithZeroValue() {
        // Given
        Culto culto = Culto.builder().id(10L).build();

        // When
        Oferta oferta = Oferta.builder()
                .id(1L)
                .culto(culto)
                .valor(BigDecimal.ZERO)
                .build();

        // Then
        assertThat(oferta.getValor()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void testBuilderWithLargeValue() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        BigDecimal largeValue = new BigDecimal("99999999.99");

        // When
        Oferta oferta = Oferta.builder()
                .id(1L)
                .culto(culto)
                .valor(largeValue)
                .build();

        // Then
        assertThat(oferta.getValor()).isEqualByComparingTo(largeValue);
    }

    @Test
    void testBuilderWithDecimalValue() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        BigDecimal decimalValue = new BigDecimal("123.456");

        // When
        Oferta oferta = Oferta.builder()
                .id(1L)
                .culto(culto)
                .valor(decimalValue)
                .build();

        // Then
        assertThat(oferta.getValor()).isEqualByComparingTo(decimalValue);
    }
}
