package br.com.igreja.ipiranga.modules.culto.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CultoTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Culto culto = new Culto();

        // Then
        assertThat(culto).isNotNull();
        assertThat(culto.getId()).isNull();
        assertThat(culto.getDataHora()).isNull();
        assertThat(culto.getTema()).isNull();
        assertThat(culto.getPalavraInicialPor()).isNull();
        assertThat(culto.getPalavraFinalPor()).isNull();
        assertThat(culto.getTotalPessoas()).isNull();
        assertThat(culto.getStatus()).isNull();
        assertThat(culto.getIgrejaId()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        LocalDateTime dataHora = LocalDateTime.now();
        String tema = "A Graça de Deus";
        String palavraInicialPor = "Pastor João";
        String palavraFinalPor = "Pastor Maria";
        Integer totalPessoas = 100;
        Culto.StatusCulto status = Culto.StatusCulto.EM_ANDAMENTO;

        // When
        Culto culto = new Culto(id, dataHora, tema, palavraInicialPor, palavraFinalPor, totalPessoas, status);

        // Then
        assertThat(culto).isNotNull();
        assertThat(culto.getId()).isEqualTo(id);
        assertThat(culto.getDataHora()).isEqualTo(dataHora);
        assertThat(culto.getTema()).isEqualTo(tema);
        assertThat(culto.getPalavraInicialPor()).isEqualTo(palavraInicialPor);
        assertThat(culto.getPalavraFinalPor()).isEqualTo(palavraFinalPor);
        assertThat(culto.getTotalPessoas()).isEqualTo(totalPessoas);
        assertThat(culto.getStatus()).isEqualTo(status);
    }

    @Test
    void testBuilder() {
        // Given
        Long id = 1L;
        LocalDateTime dataHora = LocalDateTime.of(2024, 1, 15, 19, 30);
        String tema = "O Amor de Cristo";
        String palavraInicialPor = "Presbítero Pedro";
        String palavraFinalPor = "Pastor Ana";
        Integer totalPessoas = 150;
        Culto.StatusCulto status = Culto.StatusCulto.FINALIZADO;
        Long igrejaId = 5L;

        // When
        Culto culto = Culto.builder()
                .id(id)
                .dataHora(dataHora)
                .tema(tema)
                .palavraInicialPor(palavraInicialPor)
                .palavraFinalPor(palavraFinalPor)
                .totalPessoas(totalPessoas)
                .status(status)
                .igrejaId(igrejaId)
                .build();

        // Then
        assertThat(culto).isNotNull();
        assertThat(culto.getId()).isEqualTo(id);
        assertThat(culto.getDataHora()).isEqualTo(dataHora);
        assertThat(culto.getTema()).isEqualTo(tema);
        assertThat(culto.getPalavraInicialPor()).isEqualTo(palavraInicialPor);
        assertThat(culto.getPalavraFinalPor()).isEqualTo(palavraFinalPor);
        assertThat(culto.getTotalPessoas()).isEqualTo(totalPessoas);
        assertThat(culto.getStatus()).isEqualTo(status);
        assertThat(culto.getIgrejaId()).isEqualTo(igrejaId);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Culto culto = new Culto();
        Long id = 2L;
        LocalDateTime dataHora = LocalDateTime.of(2024, 2, 20, 10, 0);
        String tema = "A Fé que Move Montanhas";
        String palavraInicialPor = "Diácono Carlos";
        String palavraFinalPor = "Pastor Roberto";
        Integer totalPessoas = 200;
        Culto.StatusCulto status = Culto.StatusCulto.EM_ANDAMENTO;
        Long igrejaId = 10L;

        // When
        culto.setId(id);
        culto.setDataHora(dataHora);
        culto.setTema(tema);
        culto.setPalavraInicialPor(palavraInicialPor);
        culto.setPalavraFinalPor(palavraFinalPor);
        culto.setTotalPessoas(totalPessoas);
        culto.setStatus(status);
        culto.setIgrejaId(igrejaId);

        // Then
        assertThat(culto.getId()).isEqualTo(id);
        assertThat(culto.getDataHora()).isEqualTo(dataHora);
        assertThat(culto.getTema()).isEqualTo(tema);
        assertThat(culto.getPalavraInicialPor()).isEqualTo(palavraInicialPor);
        assertThat(culto.getPalavraFinalPor()).isEqualTo(palavraFinalPor);
        assertThat(culto.getTotalPessoas()).isEqualTo(totalPessoas);
        assertThat(culto.getStatus()).isEqualTo(status);
        assertThat(culto.getIgrejaId()).isEqualTo(igrejaId);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        LocalDateTime dataHora = LocalDateTime.now();
        Culto culto1 = Culto.builder()
                .id(1L)
                .dataHora(dataHora)
                .tema("Tema 1")
                .status(Culto.StatusCulto.EM_ANDAMENTO)
                .igrejaId(5L)
                .build();

        Culto culto2 = Culto.builder()
                .id(1L)
                .dataHora(dataHora)
                .tema("Tema 1")
                .status(Culto.StatusCulto.EM_ANDAMENTO)
                .igrejaId(5L)
                .build();

        Culto culto3 = Culto.builder()
                .id(2L)
                .dataHora(dataHora)
                .tema("Tema 2")
                .status(Culto.StatusCulto.FINALIZADO)
                .igrejaId(10L)
                .build();

        // Then
        assertThat(culto1).isEqualTo(culto2);
        assertThat(culto1).hasSameHashCodeAs(culto2);
        assertThat(culto1).isNotEqualTo(culto3);
        assertThat(culto1.hashCode()).isNotEqualTo(culto3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        Culto culto = Culto.builder()
                .id(1L)
                .dataHora(LocalDateTime.of(2024, 1, 15, 19, 30))
                .tema("A Graça de Deus")
                .status(Culto.StatusCulto.EM_ANDAMENTO)
                .igrejaId(5L)
                .build();

        // When
        String toString = culto.toString();

        // Then
        assertThat(toString).contains("Culto");
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("tema=A Graça de Deus");
        assertThat(toString).contains("status=EM_ANDAMENTO");
    }

    @Test
    void testStatusCultoEnum() {
        // Test all enum values
        assertThat(Culto.StatusCulto.values()).containsExactlyInAnyOrder(
                Culto.StatusCulto.EM_ANDAMENTO,
                Culto.StatusCulto.FINALIZADO
        );

        // Test enum valueOf
        assertThat(Culto.StatusCulto.valueOf("EM_ANDAMENTO")).isEqualTo(Culto.StatusCulto.EM_ANDAMENTO);
        assertThat(Culto.StatusCulto.valueOf("FINALIZADO")).isEqualTo(Culto.StatusCulto.FINALIZADO);
    }

    @Test
    void testBuilderWithNullOptionalFields() {
        // Given
        LocalDateTime dataHora = LocalDateTime.now();
        Culto.StatusCulto status = Culto.StatusCulto.EM_ANDAMENTO;

        // When
        Culto culto = Culto.builder()
                .id(1L)
                .dataHora(dataHora)
                .tema(null)
                .palavraInicialPor(null)
                .palavraFinalPor(null)
                .totalPessoas(null)
                .status(status)
                .build();

        // Then
        assertThat(culto.getTema()).isNull();
        assertThat(culto.getPalavraInicialPor()).isNull();
        assertThat(culto.getPalavraFinalPor()).isNull();
        assertThat(culto.getTotalPessoas()).isNull();
    }

    @Test
    void testInheritedTenantEntityFields() {
        // Given
        Culto culto = new Culto();
        Long igrejaId = 100L;

        // When
        culto.setIgrejaId(igrejaId);

        // Then
        assertThat(culto.getIgrejaId()).isEqualTo(igrejaId);
    }
}
