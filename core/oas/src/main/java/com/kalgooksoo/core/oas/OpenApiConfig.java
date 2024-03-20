package com.kalgooksoo.core.oas;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

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
    public SwaggerUiConfigProperties swaggerUiConfigProperties() {
        return new SwaggerUiConfigProperties();
    }

    @Bean
    public SwaggerUiConfigParameters swaggerUiConfigParameters(SwaggerUiConfigProperties swaggerUiConfigProperties) {
        SwaggerUiConfigParameters parameters = new SwaggerUiConfigParameters(swaggerUiConfigProperties);
        parameters.setPath("/swagger");
        parameters.setDisplayRequestDuration(true);
        parameters.setOperationsSorter("alpha");
        return parameters;
    }

    /**
     * Pageable 관련 파라미터를 제거하는 OperationCustomizer 빈을 생성합니다.
     * 페이지네이션 page, size 파라미터가 자동 생성되는 옵션을 제거합니다.
     *
     * @return OperationCustomizer 빈
     */
    @Bean
    public OperationCustomizer removePageableOperationCustomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            if (operation.getParameters() != null) {
                operation.getParameters().removeIf(parameter -> parameter.getName().equals("page") || parameter.getName().equals("size"));
            }
            return operation;
        };
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