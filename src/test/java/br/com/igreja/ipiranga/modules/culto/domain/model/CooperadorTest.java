package br.com.igreja.ipiranga.modules.culto.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CooperadorTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Cooperador cooperador = new Cooperador();

        // Then
        assertThat(cooperador).isNotNull();
        assertThat(cooperador.getId()).isNull();
        assertThat(cooperador.getCulto()).isNull();
        assertThat(cooperador.getNome()).isNull();
        assertThat(cooperador.getCargo()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "João Silva";
        String cargo = "Diácono";

        // When
        Cooperador cooperador = new Cooperador(id, culto, nome, cargo);

        // Then
        assertThat(cooperador).isNotNull();
        assertThat(cooperador.getId()).isEqualTo(id);
        assertThat(cooperador.getCulto()).isEqualTo(culto);
        assertThat(cooperador.getNome()).isEqualTo(nome);
        assertThat(cooperador.getCargo()).isEqualTo(cargo);
    }

    @Test
    void testBuilder() {
        // Given
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "Maria Santos";
        String cargo = "Secretária";

        // When
        Cooperador cooperador = Cooperador.builder()
                .id(id)
                .culto(culto)
                .nome(nome)
                .cargo(cargo)
                .build();

        // Then
        assertThat(cooperador).isNotNull();
        assertThat(cooperador.getId()).isEqualTo(id);
        assertThat(cooperador.getCulto()).isEqualTo(culto);
        assertThat(cooperador.getNome()).isEqualTo(nome);
        assertThat(cooperador.getCargo()).isEqualTo(cargo);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Cooperador cooperador = new Cooperador();
        Long id = 1L;
        Culto culto = Culto.builder().id(10L).build();
        String nome = "Pedro Costa";
        String cargo = "Tesoureiro";

        // When
        cooperador.setId(id);
        cooperador.setCulto(culto);
        cooperador.setNome(nome);
        cooperador.setCargo(cargo);

        // Then
        assertThat(cooperador.getId()).isEqualTo(id);
        assertThat(cooperador.getCulto()).isEqualTo(culto);
        assertThat(cooperador.getNome()).isEqualTo(nome);
        assertThat(cooperador.getCargo()).isEqualTo(cargo);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        Cooperador cooperador1 = Cooperador.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .cargo("Diácono")
                .build();

        Cooperador cooperador2 = Cooperador.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .cargo("Diácono")
                .build();

        Cooperador cooperador3 = Cooperador.builder()
                .id(2L)
                .culto(culto)
                .nome("Maria Santos")
                .cargo("Secretária")
                .build();

        // Then
        assertThat(cooperador1).isEqualTo(cooperador2);
        assertThat(cooperador1).hasSameHashCodeAs(cooperador2);
        assertThat(cooperador1).isNotEqualTo(cooperador3);
        assertThat(cooperador1.hashCode()).isNotEqualTo(cooperador3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        Culto culto = Culto.builder().id(10L).build();
        Cooperador cooperador = Cooperador.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .cargo("Diácono")
                .build();

        // When
        String toString = cooperador.toString();

        // Then
        assertThat(toString).contains("Cooperador");
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("nome=João Silva");
        assertThat(toString).contains("cargo=Diácono");
    }

    @Test
    void testBuilderWithNullCargo() {
        // Given
        Culto culto = Culto.builder().id(10L).build();

        // When
        Cooperador cooperador = Cooperador.builder()
                .id(1L)
                .culto(culto)
                .nome("João Silva")
                .cargo(null)
                .build();

        // Then
        assertThat(cooperador.getCargo()).isNull();
    }
}
