# API-GATEWAY(API 게이트웨이)
1. [소개](#소개)
2. [의존성](#의존성)
3. [설정](#설정)
4. [유레카 클라이언트](#유레카-클라이언트)
5. [외부 환경설정 바인딩](#외부-환경설정-바인딩)
6. [로드 밸런싱 및 리버스 프록시](#로드-밸런싱-및-리버스-프록시)
7. [기타](#기타)
8. [TODO](#todo)

## 소개
`API-GATEWAY`는 Netflix Eureka Client이며, 그리고 Spring Cloud Gateway를 사용하여 클라이언트 요청을 라우팅합니다.

## 의존성
이 서비스는 다음의 의존성을 기본으로 가집니다:
- `spring-boot-starter-web`
- `spring-cloud-starter-netflix-eureka-client`
- `spring-cloud-starter-gateway`

## 설정
`application.yaml` 파일에서 서비스의 포트를 `8060`으로 설정합니다. 이 포트는 기본 포트로 사용됩니다.
```yaml
server:
  port: 8060
```

## 유레카 클라이언트
이 서비스는 유레카 클라이언트로써, 유레카 서버에게 요청하여 서비스 등록을 합니다. 이 포트는 기본 포트로 사용됩니다.
```yaml
eureka:
  client:
    service-url:
      default-zone: http://localhost:8761/eureka/
```
```java
/**
 * API 게이트웨이 애플리케이션
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
```

## 외부 환경설정 바인딩
이 서비스는 `config-server`로부터 설정을 가져옵니다. 이 포트는 기본 포트로 사용됩니다.
```yaml
spring:
  application:
    name: api-gateway
  config:
    import: 'optional:configserver:http://localhost:8888'
```

## 로드 밸런싱 및 리버스 프록시
API Gateway는 각 마이크로 서비스에게 로드 밸런싱 및 리버스 프록시를 명세합니다.<br>
`spring.main.web-application-type: reactive`를 사용하여 리액티브 웹 애플리케이션을 사용합니다.<br>
`reactive`로 설정하면 `Mono`, `Flux` 등의 리액티브 타입을 사용할 수 있습니다.<br>
하지만 Servlet 기반의 웹 애플리케이션으로 사용하려면 타입을 `servlet`으로 설정합니다.

`spring.cloud.gateway.routes`의 id는 각 라우트를 고유하게 식별하는 임의의 값입니다.<br>
이 값은 유일해야 하며, 라우트 구성 간에 중복될 수 없습니다.<br>
그러나 일반적으로, 이 id는 대상 서비스의 `spring.application.name` 값과 일치하도록 설정하는 것이 좋습니다. 이렇게 하면 라우트 설정이 어떤 서비스를 대상으로 하는지 쉽게 이해할 수 있습니다. 하지만 이는 규칙이나 필수 사항이 아니라, 단지 관례입니다.
```yaml
spring:
  application:
    name: api-gateway
  main:
    web-application-type: reactive
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/users/**
        - id: auth-service
          uri: lb://auth-service
          predicates:
            - Path=/auth/**
```

## 기타
`api-gateway`에서 인증 및 인가에 대해서 필터링을 할 수는 있으나 하지않았습니다.<br>
왜냐하면 `api-gateway`가 미들웨어로 이관되었을 경우 애플리케이션과 강하게 결합한 경우 수정작업이 많이 필요할 가능성이 있기 때문에 이를 고려하여 하지 않았습니다.<br>
하지만 필요하다면 `spring-cloud-gateway`에서 제공하는 필터를 사용하여 인증 및 인가를 할 수 있습니다.