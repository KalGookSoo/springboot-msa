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
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class TokenValidationFilter extends AbstractGatewayFilterFactory<TokenValidationFilter.Config> {

    private final Logger logger = LoggerFactory.getLogger(TokenValidationFilter.class);

    private final WebClient webClient;

    public TokenValidationFilter(WebClient webClient) {
        super(Config.class);
        this.webClient = webClient;
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
                    .doOnNext(jsonNode -> {
                        // 검증 결과에서 보안 주체명을 추출하여 파라미터에 추가합니다.
                        String username = jsonNode.get("principal").asText();

                        // 헤더에 username을 추가합니다.
                        ServerHttpRequest request = exchange.getRequest().mutate()
                                .header("username", username)
                                .build();

                        // ServerWebExchange를 업데이트합니다.
                        exchange.mutate().request(request).build();
                    })
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