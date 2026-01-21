package br.com.igreja.ipiranga.modules.culto.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MusicoTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Musico musico = new Musico();

        // Then
        assertThat(musico).isNotNull();
        assertThat(musico.getId()).isNull();
        assertThat(musico.getCulto()).isNull();
        assertThat(musico.getNome()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "João Silva";

        // When
        Musico musico = new Musico(id, culto, nome);

        // Then
        assertThat(musico).isNotNull();
        assertThat(musico.getId()).isEqualTo(id);
        assertThat(musico.getCulto()).isEqualTo(culto);
        assertThat(musico.getNome()).isEqualTo(nome);
    }

    @Test
    void testBuilder() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "Maria Santos";

        // When
        Musico musico = Musico.builder()
                .id(id)
                .culto(culto)
                .nome(nome)
                .build();

        // Then
        assertThat(musico).isNotNull();
        assertThat(musico.getId()).isEqualTo(id);
        assertThat(musico.getCulto()).isEqualTo(culto);
        assertThat(musico.getNome()).isEqualTo(nome);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Musico musico = new Musico();
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "Pedro Costa";

        // When
        musico.setId(id);
        musico.setCulto(culto);
        musico.setNome(nome);

        // Then
        assertThat(musico.getId()).isEqualTo(id);
        assertThat(musico.getCulto()).isEqualTo(culto);
        assertThat(musico.getNome()).isEqualTo(nome);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        Musico musico1 = Musico.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .build();

        Musico musico2 = Musico.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .build();

        Musico musico3 = Musico.builder()
                .id(2L)
                .culto(culto)
                .nome("Maria Santos")
                .build();

        // Then
        assertThat(musico1).isEqualTo(musico2);
        assertThat(musico1).hasSameHashCodeAs(musico2);
        assertThat(musico1).isNotEqualTo(musico3);
        assertThat(musico1.hashCode()).isNotEqualTo(musico3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        Musico musico = Musico.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .build();

        // When
        String toString = musico.toString();

        // Then
        assertThat(toString).contains("Musico");
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("nome=João Silva");
    }
}
