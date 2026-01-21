package br.com.igreja.ipiranga.modules.audit.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class LogCorrecaoTest {

    @Test
    void builder_ShouldCreateLogCorrecaoWithAllFields() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        LogCorrecao log = LogCorrecao.builder()
                .id(1L)
                .entidadeTipo("Usuario")
                .entidadeId(10L)
                .acao("INSERT")
                .usuarioId(5L)
                .timestamp(timestamp)
                .valorAntigo("old value")
                .valorNovo("new value")
                .build();

        // Assert
        assertThat(log).isNotNull();
        assertThat(log.getId()).isEqualTo(1L);
        assertThat(log.getEntidadeTipo()).isEqualTo("Usuario");
        assertThat(log.getEntidadeId()).isEqualTo(10L);
        assertThat(log.getAcao()).isEqualTo("INSERT");
        assertThat(log.getUsuarioId()).isEqualTo(5L);
        assertThat(log.getTimestamp()).isEqualTo(timestamp);
        assertThat(log.getValorAntigo()).isEqualTo("old value");
        assertThat(log.getValorNovo()).isEqualTo("new value");
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyLogCorrecao() {
        // Act
        LogCorrecao log = new LogCorrecao();

        // Assert
        assertThat(log).isNotNull();
        assertThat(log.getId()).isNull();
        assertThat(log.getEntidadeTipo()).isNull();
        assertThat(log.getEntidadeId()).isNull();
        assertThat(log.getAcao()).isNull();
        assertThat(log.getUsuarioId()).isNull();
        assertThat(log.getTimestamp()).isNull();
        assertThat(log.getValorAntigo()).isNull();
        assertThat(log.getValorNovo()).isNull();
    }

    @Test
    void allArgsConstructor_ShouldCreateLogCorrecaoWithAllArguments() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        LogCorrecao log = new LogCorrecao(
                1L,
                "Culto",
                100L,
                "UPDATE",
                20L,
                timestamp,
                "old culto data",
                "new culto data"
        );

        // Assert
        assertThat(log).isNotNull();
        assertThat(log.getId()).isEqualTo(1L);
        assertThat(log.getEntidadeTipo()).isEqualTo("Culto");
        assertThat(log.getEntidadeId()).isEqualTo(100L);
        assertThat(log.getAcao()).isEqualTo("UPDATE");
        assertThat(log.getUsuarioId()).isEqualTo(20L);
        assertThat(log.getTimestamp()).isEqualTo(timestamp);
        assertThat(log.getValorAntigo()).isEqualTo("old culto data");
        assertThat(log.getValorNovo()).isEqualTo("new culto data");
    }

    @Test
    void setters_ShouldUpdateFields() {
        // Arrange
        LogCorrecao log = new LogCorrecao();
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        log.setId(99L);
        log.setEntidadeTipo("Oferta");
        log.setEntidadeId(55L);
        log.setAcao("DELETE");
        log.setUsuarioId(33L);
        log.setTimestamp(timestamp);
        log.setValorAntigo("deleted value");
        log.setValorNovo(null);

        // Assert
        assertThat(log.getId()).isEqualTo(99L);
        assertThat(log.getEntidadeTipo()).isEqualTo("Oferta");
        assertThat(log.getEntidadeId()).isEqualTo(55L);
        assertThat(log.getAcao()).isEqualTo("DELETE");
        assertThat(log.getUsuarioId()).isEqualTo(33L);
        assertThat(log.getTimestamp()).isEqualTo(timestamp);
        assertThat(log.getValorAntigo()).isEqualTo("deleted value");
        assertThat(log.getValorNovo()).isNull();
    }

    @Test
    void equals_WithSameData_ShouldReturnTrue() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        LogCorrecao log1 = LogCorrecao.builder()
                .id(1L)
                .entidadeTipo("Usuario")
                .acao("INSERT")
                .timestamp(timestamp)
                .build();

        LogCorrecao log2 = LogCorrecao.builder()
                .id(1L)
                .entidadeTipo("Usuario")
                .acao("INSERT")
                .timestamp(timestamp)
                .build();

        // Act & Assert
        assertThat(log1).isEqualTo(log2);
    }

    @Test
    void hashCode_WithSameData_ShouldReturnSameHashCode() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        LogCorrecao log1 = LogCorrecao.builder()
                .id(1L)
                .entidadeTipo("Usuario")
                .acao("INSERT")
                .timestamp(timestamp)
                .build();

        LogCorrecao log2 = LogCorrecao.builder()
                .id(1L)
                .entidadeTipo("Usuario")
                .acao("INSERT")
                .timestamp(timestamp)
                .build();

        // Act & Assert
        assertThat(log1.hashCode()).isEqualTo(log2.hashCode());
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        // Arrange
        LogCorrecao log = LogCorrecao.builder()
                .id(1L)
                .entidadeTipo("Usuario")
                .acao("INSERT")
                .build();

        // Act
        String result = log.toString();

        // Assert
        assertThat(result).contains("LogCorrecao");
        assertThat(result).contains("id=1");
        assertThat(result).contains("entidadeTipo=Usuario");
        assertThat(result).contains("acao=INSERT");
    }
}
