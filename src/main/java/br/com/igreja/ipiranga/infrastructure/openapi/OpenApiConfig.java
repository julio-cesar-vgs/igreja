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
 * Classe de configuração da documentação automática da API via SpringDoc OpenApi (Swagger 3).
 * <p>
 * Personaliza as informações gerais da API (título, descrição, versão) e define os esquemas de segurança,
 * permitindo que o botão "Authorize" no Swagger UI funcione corretamente com tokens JWT Bearer.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Configuration
public class OpenApiConfig {

    /**
     * Define o bean principal do OpenAPI com configurações personalizadas.
     *
     * @return Objeto OpenAPI contendo info e componentes de segurança configurados.
     */
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
