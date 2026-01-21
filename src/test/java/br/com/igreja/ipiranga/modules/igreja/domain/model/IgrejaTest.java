package br.com.igreja.ipiranga.modules.igreja.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IgrejaTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Igreja igreja = new Igreja();

        // Then
        assertThat(igreja).isNotNull();
        assertThat(igreja.getId()).isNull();
        assertThat(igreja.getNome()).isNull();
        assertThat(igreja.getTipo()).isNull();
        assertThat(igreja.getEndereco()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        String nome = "Igreja Ipiranga";
        Igreja.TipoIgreja tipo = Igreja.TipoIgreja.MATRIZ;
        String endereco = "Rua Ipiranga, 123";

        // When
        Igreja igreja = new Igreja(id, nome, tipo, endereco);

        // Then
        assertThat(igreja).isNotNull();
        assertThat(igreja.getId()).isEqualTo(id);
        assertThat(igreja.getNome()).isEqualTo(nome);
        assertThat(igreja.getTipo()).isEqualTo(tipo);
        assertThat(igreja.getEndereco()).isEqualTo(endereco);
    }

    @Test
    void testBuilder() {
        // Given
        Long id = 1L;
        String nome = "Igreja Central";
        Igreja.TipoIgreja tipo = Igreja.TipoIgreja.FILIAL;
        String endereco = "Av. Brasil, 456";

        // When
        Igreja igreja = Igreja.builder()
                .id(id)
                .nome(nome)
                .tipo(tipo)
                .endereco(endereco)
                .build();

        // Then
        assertThat(igreja).isNotNull();
        assertThat(igreja.getId()).isEqualTo(id);
        assertThat(igreja.getNome()).isEqualTo(nome);
        assertThat(igreja.getTipo()).isEqualTo(tipo);
        assertThat(igreja.getEndereco()).isEqualTo(endereco);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Igreja igreja = new Igreja();
        Long id = 2L;
        String nome = "Igreja do Bairro";
        Igreja.TipoIgreja tipo = Igreja.TipoIgreja.MATRIZ;
        String endereco = "Rua das Flores, 789";

        // When
        igreja.setId(id);
        igreja.setNome(nome);
        igreja.setTipo(tipo);
        igreja.setEndereco(endereco);

        // Then
        assertThat(igreja.getId()).isEqualTo(id);
        assertThat(igreja.getNome()).isEqualTo(nome);
        assertThat(igreja.getTipo()).isEqualTo(tipo);
        assertThat(igreja.getEndereco()).isEqualTo(endereco);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Igreja igreja1 = Igreja.builder()
                .id(1L)
                .nome("Igreja Ipiranga")
                .tipo(Igreja.TipoIgreja.MATRIZ)
                .endereco("Rua Ipiranga, 123")
                .build();

        Igreja igreja2 = Igreja.builder()
                .id(1L)
                .nome("Igreja Ipiranga")
                .tipo(Igreja.TipoIgreja.MATRIZ)
                .endereco("Rua Ipiranga, 123")
                .build();

        Igreja igreja3 = Igreja.builder()
                .id(2L)
                .nome("Igreja Central")
                .tipo(Igreja.TipoIgreja.FILIAL)
                .endereco("Av. Brasil, 456")
                .build();

        // Then
        assertThat(igreja1).isEqualTo(igreja2);
        assertThat(igreja1).hasSameHashCodeAs(igreja2);
        assertThat(igreja1).isNotEqualTo(igreja3);
        assertThat(igreja1.hashCode()).isNotEqualTo(igreja3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        Igreja igreja = Igreja.builder()
                .id(1L)
                .nome("Igreja Ipiranga")
                .tipo(Igreja.TipoIgreja.MATRIZ)
                .endereco("Rua Ipiranga, 123")
                .build();

        // When
        String toString = igreja.toString();

        // Then
        assertThat(toString).contains("Igreja");
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("nome=Igreja Ipiranga");
        assertThat(toString).contains("tipo=MATRIZ");
        assertThat(toString).contains("endereco=Rua Ipiranga, 123");
    }

    @Test
    void testTipoIgrejaEnum() {
        // Test all enum values
        assertThat(Igreja.TipoIgreja.values()).containsExactlyInAnyOrder(
                Igreja.TipoIgreja.MATRIZ,
                Igreja.TipoIgreja.FILIAL
        );

        // Test enum valueOf
        assertThat(Igreja.TipoIgreja.valueOf("MATRIZ")).isEqualTo(Igreja.TipoIgreja.MATRIZ);
        assertThat(Igreja.TipoIgreja.valueOf("FILIAL")).isEqualTo(Igreja.TipoIgreja.FILIAL);
    }

    @Test
    void testBuilderWithMatrizType() {
        // When
        Igreja igreja = Igreja.builder()
                .id(1L)
                .nome("Igreja Matriz")
                .tipo(Igreja.TipoIgreja.MATRIZ)
                .endereco("Endereço Matriz")
                .build();

        // Then
        assertThat(igreja.getTipo()).isEqualTo(Igreja.TipoIgreja.MATRIZ);
    }

    @Test
    void testBuilderWithFilialType() {
        // When
        Igreja igreja = Igreja.builder()
                .id(2L)
                .nome("Igreja Filial")
                .tipo(Igreja.TipoIgreja.FILIAL)
                .endereco("Endereço Filial")
                .build();

        // Then
        assertThat(igreja.getTipo()).isEqualTo(Igreja.TipoIgreja.FILIAL);
    }

    @Test
    void testBuilderWithNullEndereco() {
        // When
        Igreja igreja = Igreja.builder()
                .id(1L)
                .nome("Igreja Sem Endereço")
                .tipo(Igreja.TipoIgreja.MATRIZ)
                .endereco(null)
                .build();

        // Then
        assertThat(igreja.getEndereco()).isNull();
    }

    @Test
    void testBuilderWithEmptyEndereco() {
        // When
        Igreja igreja = Igreja.builder()
                .id(1L)
                .nome("Igreja")
                .tipo(Igreja.TipoIgreja.MATRIZ)
                .endereco("")
                .build();

        // Then
        assertThat(igreja.getEndereco()).isEmpty();
    }
}
