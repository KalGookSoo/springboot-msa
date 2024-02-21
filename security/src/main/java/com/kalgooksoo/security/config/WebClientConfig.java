package com.kalgooksoo.security.config;

import com.kalgooksoo.security.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * WebClient 설정을 담당하는 클래스입니다.
 * WebClient를 생성하고, UserClient 인터페이스를 구현하는 프록시를 생성합니다.
 */
@RequiredArgsConstructor
@Configuration
public class WebClientConfig {

    private final LoadBalancedExchangeFilterFunction filterFunction;

    /**
     * WebClient를 생성하는 메서드입니다.
     * LoadBalancedExchangeFilterFunction을 이용하여 Load Balancer를 설정합니다.
     *
     * @return WebClient 객체
     */
    @Bean
    public WebClient userWebClient() {
        return WebClient.builder().baseUrl("http://user-service")
                .filter(filterFunction)
                .build();
    }

    /**
     * UserClient 인터페이스를 구현하는 프록시를 생성하는 메서드입니다.
     * WebClientAdapter와 HttpServiceProxyFactory를 이용하여 프록시를 생성합니다.
     *
     * @return UserClient 인터페이스를 구현하는 프록시
     * @see <a href="https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#rest-http-interface">Spring Framework Documentation</a>
     */
    @Bean
    public UserClient userClient() {
        WebClientAdapter adapter = WebClientAdapter.create(userWebClient());
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(UserClient.class);
    }

}