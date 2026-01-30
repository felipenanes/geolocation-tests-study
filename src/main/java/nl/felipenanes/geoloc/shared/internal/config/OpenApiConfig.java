package nl.felipenanes.geoloc.shared.internal.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development"),
                        new Server().url("https://api.geoloc.com").description("Production")
                ))
                .tags(List.of(
                        new Tag().name("Authentication").description("User authentication endpoints"),
                        new Tag().name("Locations").description("Store location and proximity endpoints")
                ))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("GeoLoc API Documentation")
                        .url("https://github.com/felipenanes/locations-study-test"));
    }

    private Info apiInfo() {
        return new Info()
                .title("GeoLoc API")
                .description("RESTful API for finding nearest stores based on geographic coordinates")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Felipe Nanes")
                        .email("felipe.nanes@example.com")
                        .url("https://github.com/felipenanes/locations-study-test")
                )
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT")
                );
    }
}
