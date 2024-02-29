package com.kalgooksoo.apigateway.config;

import com.kalgooksoo.apigateway.filter.TokenValidationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, TokenValidationFilter tokenValidationFilter) {
        return builder.routes()
                .route("user-service", p -> p
                        .path("/users/**")
                        .filters(f -> f.filter(tokenValidationFilter.apply(new TokenValidationFilter.Config())))
                        .uri("lb://user-service"))

                .route("security-service", p -> p
                        .path("/auth/**")
                        .uri("lb://security-service"))

                .build();
    }

}