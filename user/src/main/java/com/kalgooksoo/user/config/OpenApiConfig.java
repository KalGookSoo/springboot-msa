package com.kalgooksoo.user.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI 설정을 위한 클래스입니다.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    @Bean
    public SwaggerUiConfigParameters swaggerUiConfigParameters(SwaggerUiConfigProperties swaggerUiConfigProperties) {
        SwaggerUiConfigParameters parameters = new SwaggerUiConfigParameters(swaggerUiConfigProperties);
        parameters.setPath("/swagger");
        parameters.setDisplayRequestDuration(true);
        parameters.setOperationsSorter("alpha");
        return parameters;
    }

    private Info apiInfo() {
        String title = "Spring Boot OpenAPI";
        String version = "1.0.0";
        return new Info()
                .title(title)
                .description("API 명세")
                .version(version)
                .contact(new Contact().name("kimdoyeob").email("look3915@naver.com").url("https://github.com/KalGookSoo/springboot-msa"))
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));
    }

}