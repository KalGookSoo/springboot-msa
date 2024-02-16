### Eco System
- Java 17
- Gradle
- Spring Boot 3.2.2
- Spring Cloud Config Server: git 저장소를 기반으로 한 중앙 집중식 외부 구성 관리. 구성 리소스는 직접적으로 Spring `Environment`에 매핑되지만, 필요한 경우 비-Spring 애플리케이션에서도 사용할 수 있습니다.
- Spring Cloud Netflix Eureka: Spring 애플리케이션과 다른 애플리케이션 모두에 의한 등록을 지원하는 서비스 등록 및 발견.
- Spring Cloud Netflix Zuul: 동적 라우팅, 모니터링, 복원력, 보안 등을 제공합니다.

### 구동 절차
1. ConfigServerApplication 실행
2. ServiceRegistryApplication 실행
3. GatewayApplication 실행
4. 그 외 MicroserviceApplication 실행

###