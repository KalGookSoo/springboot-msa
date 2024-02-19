package com.kalgooksoo.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    public AuthorizationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // TODO: 인증 및 인가 로직 구현
            // 예를 들어, 헤더에서 토큰을 가져와서 검증할 수 있습니다.
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (token == null || !isValid(token)) {
                throw new RuntimeException("Invalid token");
            }
            return chain.filter(exchange);
        };
    }

    private boolean isValid(String token) {
        // TODO: 토큰 검증 로직 구현
        return true;
    }

    public static class Config {

    }

}