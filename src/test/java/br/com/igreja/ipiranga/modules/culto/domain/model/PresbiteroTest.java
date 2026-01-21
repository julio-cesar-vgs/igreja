package br.com.igreja.ipiranga.modules.culto.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PresbiteroTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Presbitero presbitero = new Presbitero();

        // Then
        assertThat(presbitero).isNotNull();
        assertThat(presbitero.getId()).isNull();
        assertThat(presbitero.getCulto()).isNull();
        assertThat(presbitero.getNome()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "João Silva";

        // When
        Presbitero presbitero = new Presbitero(id, culto, nome);

        // Then
        assertThat(presbitero).isNotNull();
        assertThat(presbitero.getId()).isEqualTo(id);
        assertThat(presbitero.getCulto()).isEqualTo(culto);
        assertThat(presbitero.getNome()).isEqualTo(nome);
    }

    @Test
    void testBuilder() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "Maria Santos";

        // When
        Presbitero presbitero = Presbitero.builder()
                .id(id)
                .culto(culto)
                .nome(nome)
                .build();

        // Then
        assertThat(presbitero).isNotNull();
        assertThat(presbitero.getId()).isEqualTo(id);
        assertThat(presbitero.getCulto()).isEqualTo(culto);
        assertThat(presbitero.getNome()).isEqualTo(nome);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Presbitero presbitero = new Presbitero();
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "Pedro Costa";

        // When
        presbitero.setId(id);
        presbitero.setCulto(culto);
        presbitero.setNome(nome);

        // Then
        assertThat(presbitero.getId()).isEqualTo(id);
        assertThat(presbitero.getCulto()).isEqualTo(culto);
        assertThat(presbitero.getNome()).isEqualTo(nome);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        Presbitero presbitero1 = Presbitero.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .build();

        Presbitero presbitero2 = Presbitero.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .build();

        Presbitero presbitero3 = Presbitero.builder()
                .id(2L)
                .culto(culto)
                .nome("Maria Santos")
                .build();

        // Then
        assertThat(presbitero1).isEqualTo(presbitero2);
        assertThat(presbitero1).hasSameHashCodeAs(presbitero2);
        assertThat(presbitero1).isNotEqualTo(presbitero3);
        assertThat(presbitero1.hashCode()).isNotEqualTo(presbitero3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        Presbitero presbitero = Presbitero.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .build();

        // When
        String toString = presbitero.toString();

        // Then
        assertThat(toString).contains("Presbitero");
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("nome=João Silva");
    }
}
