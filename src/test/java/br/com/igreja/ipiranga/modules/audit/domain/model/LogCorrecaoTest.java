package br.com.igreja.ipiranga.modules.audit.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LogCorrecaoTest {

    @Test
    void builder_ShouldCreateLogCorrecaoWithAllFields() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        UUID id = UUID.randomUUID();
        UUID entidadeId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();

        // Act
        LogCorrecao log = LogCorrecao.builder()
                .id(id)
                .entidadeTipo("Usuario")
                .entidadeId(entidadeId)
                .acao("INSERT")
                .usuarioId(usuarioId)
                .timestamp(timestamp)
                .valorAntigo("old value")
                .valorNovo("new value")
                .build();

        // Assert
        assertThat(log).isNotNull();
        assertThat(log.getId()).isEqualTo(id);
        assertThat(log.getEntidadeTipo()).isEqualTo("Usuario");
        assertThat(log.getEntidadeId()).isEqualTo(entidadeId);
        assertThat(log.getAcao()).isEqualTo("INSERT");
        assertThat(log.getUsuarioId()).isEqualTo(usuarioId);
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
        UUID id = UUID.randomUUID();
        UUID entidadeId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();

        // Act
        LogCorrecao log = new LogCorrecao(
                id,
                "Culto",
                entidadeId,
                "UPDATE",
                usuarioId,
                timestamp,
                "old culto data",
                "new culto data");

        // Assert
        assertThat(log).isNotNull();
        assertThat(log.getId()).isEqualTo(id);
        assertThat(log.getEntidadeTipo()).isEqualTo("Culto");
        assertThat(log.getEntidadeId()).isEqualTo(entidadeId);
        assertThat(log.getAcao()).isEqualTo("UPDATE");
        assertThat(log.getUsuarioId()).isEqualTo(usuarioId);
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
        UUID id = UUID.randomUUID();
        UUID entidadeId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();

        // Act
        log.setId(id);
        log.setEntidadeTipo("Oferta");
        log.setEntidadeId(entidadeId);
        log.setAcao("DELETE");
        log.setUsuarioId(usuarioId);
        log.setTimestamp(timestamp);
        log.setValorAntigo("deleted value");
        log.setValorNovo(null);

        // Assert
        assertThat(log.getId()).isEqualTo(id);
        assertThat(log.getEntidadeTipo()).isEqualTo("Oferta");
        assertThat(log.getEntidadeId()).isEqualTo(entidadeId);
        assertThat(log.getAcao()).isEqualTo("DELETE");
        assertThat(log.getUsuarioId()).isEqualTo(usuarioId);
        assertThat(log.getTimestamp()).isEqualTo(timestamp);
        assertThat(log.getValorAntigo()).isEqualTo("deleted value");
        assertThat(log.getValorNovo()).isNull();
    }

    @Test
    void equals_WithSameData_ShouldReturnTrue() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        UUID id = UUID.randomUUID();
        LogCorrecao log1 = LogCorrecao.builder()
                .id(id)
                .entidadeTipo("Usuario")
                .acao("INSERT")
                .timestamp(timestamp)
                .build();

        LogCorrecao log2 = LogCorrecao.builder()
                .id(id)
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
        UUID id = UUID.randomUUID();
        LogCorrecao log1 = LogCorrecao.builder()
                .id(id)
                .entidadeTipo("Usuario")
                .acao("INSERT")
                .timestamp(timestamp)
                .build();

        LogCorrecao log2 = LogCorrecao.builder()
                .id(id)
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
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");
        LogCorrecao log = LogCorrecao.builder()
                .id(id)
                .entidadeTipo("Usuario")
                .acao("INSERT")
                .build();

        // Act
        String result = log.toString();

        // Assert
        assertThat(result).contains("LogCorrecao");
        assertThat(result).contains("id=00000000-0000-0000-0000-000000000001");
        assertThat(result).contains("entidadeTipo=Usuario");
        assertThat(result).contains("acao=INSERT");
    }
}
