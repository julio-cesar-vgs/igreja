package br.com.igreja.ipiranga.modules.culto.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VisitanteTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Visitante visitante = new Visitante();

        // Then
        assertThat(visitante).isNotNull();
        assertThat(visitante.getId()).isNull();
        assertThat(visitante.getCulto()).isNull();
        assertThat(visitante.getNome()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "João Silva";

        // When
        Visitante visitante = new Visitante(id, culto, nome);

        // Then
        assertThat(visitante).isNotNull();
        assertThat(visitante.getId()).isEqualTo(id);
        assertThat(visitante.getCulto()).isEqualTo(culto);
        assertThat(visitante.getNome()).isEqualTo(nome);
    }

    @Test
    void testBuilder() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "Maria Santos";

        // When
        Visitante visitante = Visitante.builder()
                .id(id)
                .culto(culto)
                .nome(nome)
                .build();

        // Then
        assertThat(visitante).isNotNull();
        assertThat(visitante.getId()).isEqualTo(id);
        assertThat(visitante.getCulto()).isEqualTo(culto);
        assertThat(visitante.getNome()).isEqualTo(nome);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Visitante visitante = new Visitante();
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "Pedro Costa";

        // When
        visitante.setId(id);
        visitante.setCulto(culto);
        visitante.setNome(nome);

        // Then
        assertThat(visitante.getId()).isEqualTo(id);
        assertThat(visitante.getCulto()).isEqualTo(culto);
        assertThat(visitante.getNome()).isEqualTo(nome);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        Visitante visitante1 = Visitante.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .build();

        Visitante visitante2 = Visitante.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .build();

        Visitante visitante3 = Visitante.builder()
                .id(2L)
                .culto(culto)
                .nome("Maria Santos")
                .build();

        // Then
        assertThat(visitante1).isEqualTo(visitante2);
        assertThat(visitante1).hasSameHashCodeAs(visitante2);
        assertThat(visitante1).isNotEqualTo(visitante3);
        assertThat(visitante1.hashCode()).isNotEqualTo(visitante3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        Visitante visitante = Visitante.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .build();

        // When
        String toString = visitante.toString();

        // Then
        assertThat(toString).contains("Visitante");
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("nome=João Silva");
    }
}
