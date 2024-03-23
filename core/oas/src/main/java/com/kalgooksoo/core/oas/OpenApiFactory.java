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
import org.springframework.web.method.HandlerMethod;

/**
 * OpenAPI 설정을 위한 클래스입니다.
 */
public class OpenApiFactory {

    private final String title;

    private final String version;

    public OpenApiFactory(String title, String version) {
        this.title = title;
        this.version = version;
    }

    public OpenAPI openAPI() {
        Info info = apiInfo();
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }

    public SwaggerUiConfigProperties swaggerUiConfigProperties() {
        return new SwaggerUiConfigProperties();
    }

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
    public OperationCustomizer removePageableOperationCustomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            if (operation.getParameters() != null) {
                operation.getParameters().removeIf(parameter -> parameter.getName().equals("page") || parameter.getName().equals("size"));
            }
            return operation;
        };
    }

    private Info apiInfo() {
        Contact contact = new Contact()
                .name("kimdoyeob")
                .email("look3915@naver.com")
                .url("https://github.com/KalGookSoo/springboot-msa");

        License license = new License()
                .name("Apache 2.0")
                .url("http://springdoc.org");

        return new Info()
                .title(this.title)
                .description("API 명세")
                .version(this.version)
                .contact(contact)
                .license(license);
    }

}