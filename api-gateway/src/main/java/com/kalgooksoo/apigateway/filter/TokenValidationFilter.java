package com.kalgooksoo.apigateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TokenValidationFilter extends AbstractGatewayFilterFactory<TokenValidationFilter.Config> {

    private final Logger logger = LoggerFactory.getLogger(TokenValidationFilter.class);

    private final WebClient webClient;

    public TokenValidationFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();// FIXME baseUrl 하드코딩 제거할 것
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            return webClient.get()
                    .uri("/auth/token")
                    .header(HttpHeaders.AUTHORIZATION, bearerToken)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                        logger.error("HttpStatus code: {}", clientResponse.statusCode());
                        return Mono.error(new RuntimeException("HttpStatus code: " + clientResponse.statusCode()));
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                        logger.error("HttpStatus code: {}", clientResponse.statusCode());
                        return Mono.error(new RuntimeException("HttpStatus code: " + clientResponse.statusCode()));
                    })
                    .bodyToMono(JsonNode.class)
                    .doOnError(e -> {
                        ServerHttpResponse response = exchange.getResponse();
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        response.setComplete();
                    })
                    .then(chain.filter(exchange))
                    .onErrorResume(e -> Mono.empty());

        };
    }

    @Getter
    @Setter
    public static class Config {
        private boolean valid;
    }

}