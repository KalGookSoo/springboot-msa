package com.kalgooksoo.security.config;

import com.kalgooksoo.security.client.UserClient;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

/**
 * WebClient 설정
 */
@RequiredArgsConstructor
@Configuration
public class WebClientConfig {

    private final LoadBalancedExchangeFilterFunction filterFunction;

    /**
     * WebClient를 생성하는 메서드입니다.
     * 연결 시도가 3초 이내에 완료되지 않으면 타임아웃이 발생하도록 설정합니다.
     * 데이터 읽기가 3초 이내에 완료되지 않으면 타임아웃이 발생하도록 설정합니다.
     * 데이터 쓰기가 3초 이내에 완료되지 않으면 타임아웃이 발생하도록 설정합니다.
     * WebClient의 기본 URL은 USER-SERVICE로 통신할 수 있도록 설정합니다.
     * LoadBalancedExchangeFilterFunction을 필터로 추가하여 로드 밸런싱을 수행합니다.
     *
     * @return WebClient 객체
     */
    @Bean
    public WebClient userWebClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(3))
                        .addHandlerLast(new WriteTimeoutHandler(3)));

        return WebClient.builder()
                .baseUrl("lb://user-service")
                .defaultHeaders(httpHeaders -> httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .clientConnector(new ReactorClientHttpConnector(httpClient))
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