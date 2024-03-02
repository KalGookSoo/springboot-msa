package com.kalgooksoo.apigateway.config;

import com.kalgooksoo.apigateway.filter.TokenValidationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {

    /**
     * RouteLocator 빈을 생성합니다.
     * TODO 이 내용을 application.yaml에 정의할 수 있도록 변경해야 함. api-gateway의 재기동 없이 config-server의 refresh만으로 라우트 변경이 가능해야 함.
     *
     * @param builder               RouteLocatorBuilder
     * @param tokenValidationFilter TokenValidationFilter
     * @return RouteLocator 빈
     */
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, TokenValidationFilter tokenValidationFilter) {
        RouteLocatorBuilder.Builder routes = builder.routes();

        routes.route("user-service-sign-up", p -> p
                .path("/users").and().method(HttpMethod.POST)
                .uri("lb://user-service"));

        routes.route("user-service-sign-in", p -> p
                .path("/users/sign-in").and().method(HttpMethod.POST)
                .uri("lb://user-service"));

        routes.route("user-service", p -> p
                .path("/users/**")
                .filters(f -> f.filter(tokenValidationFilter.apply(new TokenValidationFilter.Config())))
                .uri("lb://user-service")
        );

        routes.route("security-service", p -> p
                .path("/auth/**")
                .uri("lb://security-service"));

        return routes.build();

    }

    @Bean
    public TokenValidationFilter tokenValidationFilter() {
        return new TokenValidationFilter(webClient());
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:8081").build();// FIXME baseUrl 하드코딩 제거할 것
    }

}