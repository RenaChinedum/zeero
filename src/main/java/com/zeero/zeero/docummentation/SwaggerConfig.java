package com.zeero.zeero.docummentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Value("${app.version}")
    private String version;

    @Bean
    public OpenAPI api() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("jwt", securityScheme))
                .info(new Info()
                        .title("Todo Application API")
                        .description("API that provides CRUD operations for LMS.")
                        .version(version))
                .security(Collections.singletonList(new SecurityRequirement().addList("jwt")));
    }

    @Bean
    public GroupedOpenApi authEndpoint() {
        return GroupedOpenApi.builder()
                .group("Auth")
                .pathsToMatch("/api/v1/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi patronEndpoint() {
        return GroupedOpenApi.builder()
                .group("Users")
                .pathsToMatch("/api/v1/user/**")
                .build();
    }
    @Bean
    public GroupedOpenApi bookEndpoint() {
        return GroupedOpenApi.builder()
                .group("Task")
                .pathsToMatch("/api/v1/task/**")
                .build();
    } 

}