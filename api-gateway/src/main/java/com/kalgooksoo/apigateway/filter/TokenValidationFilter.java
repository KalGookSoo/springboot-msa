package com.kalgooksoo.apigateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TokenValidationFilter extends AbstractGatewayFilterFactory<TokenValidationFilter.Config> {

    private final WebClient webClient;

    public TokenValidationFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();// FIXME baseUrl 하드코딩 제거할 것
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            JsonNode jsonNode = webClient.get()
                    .uri("/auth/token")
                    .header(HttpHeaders.AUTHORIZATION, bearerToken)
                    .exchangeToMono(clientResponse -> clientResponse.bodyToMono(JsonNode.class))
                    .block();

            if (jsonNode == null) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {

        private boolean valid;

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

    }

}