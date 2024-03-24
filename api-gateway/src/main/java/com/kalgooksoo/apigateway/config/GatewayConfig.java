package com.kalgooksoo.apigateway.config;

import com.kalgooksoo.apigateway.filter.TokenValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {

    @Bean
    public TokenValidationFilter tokenValidationFilter() {
        return new TokenValidationFilter(webClient());
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:8081").build();// FIXME baseUrl 하드코딩 제거할 것
    }

}