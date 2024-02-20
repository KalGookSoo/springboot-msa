# AUTH-SERVICE(인증 및 인가 서비스)
1. [소개](#소개)
2. [의존성](#의존성)
3. [설정](#설정)
4. [유레카 클라이언트](#유레카-클라이언트)
5. [외부 환경설정 바인딩](#외부-환경설정-바인딩)
6. [액세스 토큰 발급](#액세스-토큰-발급)

## 소개
`AUTH-SERVICE`는 Netflix Eureka Client이며, 인증 및 인가를 부여하는 애플리케이션입니다.
- 액세스 토큰을 발급합니다.
- 액세스 토큰을 갱신합니다.
- 액세스 토큰을 검증합니다.

### 잠깐! 깨알 상식
1. 인증(Authentication): 사용자의 신원을 확인하는 과정입니다. 일반적으로 사용자 이름과 비밀번호를 사용하여 사용자가 누구인지 확인합니다. 인증 서비스는 사용자의 자격 증명을 확인하고, 이를 통해 사용자의 신원을 확인합니다.
2. 인가(Authorization): 이미 인증된 사용자가 시스템의 특정 리소스에 접근할 수 있는 권한을 부여하는 과정입니다. 인가 서비스는 사용자가 특정 작업을 수행할 권한이 있는지 확인합니다.

## 의존성
이 서비스는 다음의 의존성을 기본으로 가집니다:
- `spring-boot-starter-web`
- `spring-boot-starter-actuator`
- `spring-cloud-starter-netflix-eureka-client`
- `spring-boot-starter-webflux`

## 설정
`application.yaml` 파일에서 서비스의 포트를 `8081`으로 설정합니다.
```yaml
server:
  port: 8081
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
 * 인증 및 인가 서비스 애플리케이션
 */
@EnableDiscoveryClient
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
```

## 외부 환경설정 바인딩
이 서비스는 `config-server`로부터 설정을 가져옵니다. 이 포트는 기본 포트로 사용됩니다.
```yaml
spring:
  application:
    name: auth-service
  config:
    import: 'optional:configserver:http://localhost:8888'
```


## 액세스 토큰 발급
1. 클라이언트는 인증 서비스에게 username, password로 인증 메시지를 전송합니다.
2. 인증 서비스는 계정 서비스에게 username, password로 검증 메시지를 전송합니다.
3. 검증 결과에 따라 인증 서비스는 access token을 발급하여 클라이언트에게 응답합니다.

```mermaid
sequenceDiagram
    participant Client
    participant Auth Service
    participant User Service

    Client->>Auth Service: Send authentication message (username, password)
    Auth Service->>User Service: Send verification message (username, password)
    User Service-->>Auth Service: Verification result
    Auth Service-->>Client: Respond with access token (if verification is successful)
```