package com.kalgooksoo.article.config;

import com.kalgooksoo.core.oas.OpenApiFactory;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class OpenApiConfig {

    private final OpenApiFactory openApiFactory;

    public OpenApiConfig(
            @Value("${spring.application.name}") String title,
            @Value("${spring.application.version:1.0.0}") String version
    ) {
        this.openApiFactory = new OpenApiFactory(title, version);
    }

    @Bean
    @Primary
    public OpenAPI openAPI() {
        return openApiFactory.openAPI();
    }

    @Bean
    @Primary
    public SwaggerUiConfigProperties swaggerUiConfigProperties() {
        return openApiFactory.swaggerUiConfigProperties();
    }

    @Bean
    @Primary
    public SwaggerUiConfigParameters swaggerUiConfigParameters(SwaggerUiConfigProperties swaggerUiConfigProperties) {
        return openApiFactory.swaggerUiConfigParameters(swaggerUiConfigProperties);
    }

    @Bean
    @Primary
    public OperationCustomizer removePageableOperationCustomizer() {
        return openApiFactory.removePageableOperationCustomizer();
    }

}