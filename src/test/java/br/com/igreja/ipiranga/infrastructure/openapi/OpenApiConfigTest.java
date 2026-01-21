package br.com.igreja.ipiranga.infrastructure.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigTest {

    @Test
    void customOpenAPI_ShouldReturnConfiguredOpenAPI() {
        // Arrange
        OpenApiConfig openApiConfig = new OpenApiConfig();

        // Act
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        // Assert
        assertThat(openAPI).isNotNull();
        
        // Verify Info
        Info info = openAPI.getInfo();
        assertThat(info).isNotNull();
        assertThat(info.getTitle()).isEqualTo("Sistema de Gestão de Igrejas API");
        assertThat(info.getVersion()).isEqualTo("1.0");
        assertThat(info.getDescription()).contains("API para gestão de cultos");
        assertThat(info.getContact()).isNotNull();
        assertThat(info.getContact().getName()).isEqualTo("Suporte");
        assertThat(info.getContact().getEmail()).isEqualTo("suporte@igreja.com");
        assertThat(info.getLicense()).isNotNull();
        assertThat(info.getLicense().getName()).isEqualTo("Apache 2.0");

        // Verify Security
        assertThat(openAPI.getSecurity()).hasSize(1);
        assertThat(openAPI.getSecurity().get(0).get("bearerAuth")).isEmpty();

        // Verify Components
        assertThat(openAPI.getComponents()).isNotNull();
        assertThat(openAPI.getComponents().getSecuritySchemes()).containsKey("bearerAuth");
        
        SecurityScheme securityScheme = openAPI.getComponents().getSecuritySchemes().get("bearerAuth");
        assertThat(securityScheme.getType()).isEqualTo(SecurityScheme.Type.HTTP);
        assertThat(securityScheme.getScheme()).isEqualTo("bearer");
        assertThat(securityScheme.getBearerFormat()).isEqualTo("JWT");
    }
}
