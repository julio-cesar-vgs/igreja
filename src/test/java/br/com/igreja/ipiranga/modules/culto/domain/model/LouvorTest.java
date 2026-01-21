package br.com.igreja.ipiranga.modules.culto.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LouvorTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Louvor louvor = new Louvor();

        // Then
        assertThat(louvor).isNotNull();
        assertThat(louvor.getId()).isNull();
        assertThat(louvor.getCulto()).isNull();
        assertThat(louvor.getPessoaNome()).isNull();
        assertThat(louvor.getHinoOpcional()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String pessoaNome = "João Silva";
        String hinoOpcional = "Hino 123";

        // When
        Louvor louvor = new Louvor(id, culto, pessoaNome, hinoOpcional);

        // Then
        assertThat(louvor).isNotNull();
        assertThat(louvor.getId()).isEqualTo(id);
        assertThat(louvor.getCulto()).isEqualTo(culto);
        assertThat(louvor.getPessoaNome()).isEqualTo(pessoaNome);
        assertThat(louvor.getHinoOpcional()).isEqualTo(hinoOpcional);
    }

    @Test
    void testBuilder() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String pessoaNome = "Maria Santos";
        String hinoOpcional = "Hino 456";

        // When
        Louvor louvor = Louvor.builder()
                .id(id)
                .culto(culto)
                .pessoaNome(pessoaNome)
                .hinoOpcional(hinoOpcional)
                .build();

        // Then
        assertThat(louvor).isNotNull();
        assertThat(louvor.getId()).isEqualTo(id);
        assertThat(louvor.getCulto()).isEqualTo(culto);
        assertThat(louvor.getPessoaNome()).isEqualTo(pessoaNome);
        assertThat(louvor.getHinoOpcional()).isEqualTo(hinoOpcional);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Louvor louvor = new Louvor();
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String pessoaNome = "Pedro Costa";
        String hinoOpcional = "Hino 789";

        // When
        louvor.setId(id);
        louvor.setCulto(culto);
        louvor.setPessoaNome(pessoaNome);
        louvor.setHinoOpcional(hinoOpcional);

        // Then
        assertThat(louvor.getId()).isEqualTo(id);
        assertThat(louvor.getCulto()).isEqualTo(culto);
        assertThat(louvor.getPessoaNome()).isEqualTo(pessoaNome);
        assertThat(louvor.getHinoOpcional()).isEqualTo(hinoOpcional);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        Louvor louvor1 = Louvor.builder()
                .id(1L)
                .culto(culto)
                .pessoaNome("João Silva")
                .hinoOpcional("Hino 123")
                .build();

        Louvor louvor2 = Louvor.builder()
                .id(1L)
                .culto(culto)
                .pessoaNome("João Silva")
                .hinoOpcional("Hino 123")
                .build();

        Louvor louvor3 = Louvor.builder()
                .id(2L)
                .culto(culto)
                .pessoaNome("Maria Santos")
                .hinoOpcional("Hino 456")
                .build();

        // Then
        assertThat(louvor1).isEqualTo(louvor2);
        assertThat(louvor1).hasSameHashCodeAs(louvor2);
        assertThat(louvor1).isNotEqualTo(louvor3);
        assertThat(louvor1.hashCode()).isNotEqualTo(louvor3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        Louvor louvor = Louvor.builder()
                .id(1L)
                .culto(culto)
                .pessoaNome("João Silva")
                .hinoOpcional("Hino 123")
                .build();

        // When
        String toString = louvor.toString();

        // Then
        assertThat(toString).contains("Louvor");
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("pessoaNome=João Silva");
        assertThat(toString).contains("hinoOpcional=Hino 123");
    }

    @Test
    void testBuilderWithNullHinoOpcional() {
        // Given
        Culto culto = Culto.builder().id(10L).build();

        // When
        Louvor louvor = Louvor.builder()
                .id(1L)
                .culto(culto)
                .pessoaNome("João Silva")
                .hinoOpcional(null)
                .build();

        // Then
        assertThat(louvor.getHinoOpcional()).isNull();
    }
}
