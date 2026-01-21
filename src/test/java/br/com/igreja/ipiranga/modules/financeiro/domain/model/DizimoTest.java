package br.com.igreja.ipiranga.modules.financeiro.domain.model;

import br.com.igreja.ipiranga.modules.culto.domain.model.Culto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DizimoTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Dizimo dizimo = new Dizimo();

        // Then
        assertThat(dizimo).isNotNull();
        assertThat(dizimo.getId()).isNull();
        assertThat(dizimo.getCulto()).isNull();
        assertThat(dizimo.getNome()).isNull();
        assertThat(dizimo.getValor()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "João Silva";
        BigDecimal valor = BigDecimal.valueOf(100.00);

        // When
        Dizimo dizimo = new Dizimo(id, culto, nome, valor);

        // Then
        assertThat(dizimo).isNotNull();
        assertThat(dizimo.getId()).isEqualTo(id);
        assertThat(dizimo.getCulto()).isEqualTo(culto);
        assertThat(dizimo.getNome()).isEqualTo(nome);
        assertThat(dizimo.getValor()).isEqualByComparingTo(valor);
    }

    @Test
    void testBuilder() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "Maria Santos";
        BigDecimal valor = BigDecimal.valueOf(250.50);

        // When
        Dizimo dizimo = Dizimo.builder()
                .id(id)
                .culto(culto)
                .nome(nome)
                .valor(valor)
                .build();

        // Then
        assertThat(dizimo).isNotNull();
        assertThat(dizimo.getId()).isEqualTo(id);
        assertThat(dizimo.getCulto()).isEqualTo(culto);
        assertThat(dizimo.getNome()).isEqualTo(nome);
        assertThat(dizimo.getValor()).isEqualByComparingTo(valor);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Dizimo dizimo = new Dizimo();
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "Pedro Costa";
        BigDecimal valor = BigDecimal.valueOf(150.75);

        // When
        dizimo.setId(id);
        dizimo.setCulto(culto);
        dizimo.setNome(nome);
        dizimo.setValor(valor);

        // Then
        assertThat(dizimo.getId()).isEqualTo(id);
        assertThat(dizimo.getCulto()).isEqualTo(culto);
        assertThat(dizimo.getNome()).isEqualTo(nome);
        assertThat(dizimo.getValor()).isEqualByComparingTo(valor);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        Dizimo dizimo1 = Dizimo.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .valor(BigDecimal.valueOf(100.00))
                .build();

        Dizimo dizimo2 = Dizimo.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .valor(BigDecimal.valueOf(100.00))
                .build();

        Dizimo dizimo3 = Dizimo.builder()
                .id(2L)
                .culto(culto)
                .nome("Maria Santos")
                .valor(BigDecimal.valueOf(200.00))
                .build();

        // Then
        assertThat(dizimo1).isEqualTo(dizimo2);
        assertThat(dizimo1).hasSameHashCodeAs(dizimo2);
        assertThat(dizimo1).isNotEqualTo(dizimo3);
        assertThat(dizimo1.hashCode()).isNotEqualTo(dizimo3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        Dizimo dizimo = Dizimo.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .valor(BigDecimal.valueOf(100.00))
                .build();

        // When
        String toString = dizimo.toString();

        // Then
        assertThat(toString).contains("Dizimo");
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("nome=João Silva");
        assertThat(toString).contains("valor=100");
    }

    @Test
    void testBuilderWithZeroValue() {
        // Given
        Culto culto = Culto.builder().id(10L).build();

        // When
        Dizimo dizimo = Dizimo.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .valor(BigDecimal.ZERO)
                .build();

        // Then
        assertThat(dizimo.getValor()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void testBuilderWithLargeValue() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        BigDecimal largeValue = new BigDecimal("99999999.99");

        // When
        Dizimo dizimo = Dizimo.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .valor(largeValue)
                .build();

        // Then
        assertThat(dizimo.getValor()).isEqualByComparingTo(largeValue);
    }
}
