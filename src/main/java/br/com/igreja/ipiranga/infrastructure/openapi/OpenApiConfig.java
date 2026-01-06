package br.com.igreja.ipiranga.infrastructure.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do SpringDoc OpenAPI (Swagger).
 * Camada: Infrastructure
 * 
 * Define os metadados da documentação da API e configura o suporte global
 * para autenticação via JWT (Bearer Token) na interface visual do Swagger.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .info(new Info()
                        .title("Sistema de Gestão de Igrejas API")
                        .version("1.0")
                        .description("API para gestão de cultos, membros e finanças de igrejas (Multi-tenant).")
                        .contact(new Contact().name("Suporte").email("suporte@igreja.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
