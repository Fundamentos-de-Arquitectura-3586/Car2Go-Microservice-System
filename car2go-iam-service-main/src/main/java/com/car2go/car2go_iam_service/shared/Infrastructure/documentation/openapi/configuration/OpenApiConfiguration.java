package com.car2go.car2go_iam_service.shared.Infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración para la documentación OpenAPI (Swagger) de la aplicación CAR2GO.
 * Define información general de la API, documentación externa y esquema de seguridad JWT.
 */
@Configuration // Indica que esta clase contiene definiciones de beans para el contexto de Spring
public class OpenApiConfiguration {

    /**
     * Bean que configura el objeto OpenAPI para generar la documentación de la API.
     * Incluye título, versión, licencia, documentación externa y autenticación JWT.
     *
     * @return instancia de OpenAPI configurada.
     */
    @Bean
    public OpenAPI farmOpenApi() {
        // Configuración general de la documentación
        var openApi = new OpenAPI();
        openApi.info(new Info()
                        .title("CAR2GO API") // Título que aparecerá en la documentación
                        .version("v1.0.0")   // Versión de la API
                        .license(new License()
                                .name("Apache 2.0") // Tipo de licencia
                                .url("https://springdoc.org"))) // URL con detalles de la licencia
                .externalDocs(new ExternalDocumentation()
                        .url("https://farm/docs")); // Documentación adicional externa

        // Configuración de seguridad: autenticación con JWT (Bearer Token)
        openApi.addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // Requiere JWT para acceder a los endpoints
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP) // Tipo de seguridad HTTP
                                .scheme("bearer")               // Usar esquema Bearer
                                .bearerFormat("JWT")));         // Formato del token: JWT

        return openApi;
    }
}
