### Eco System
- Java 17
- Gradle
- Spring Boot 3.2.2
- Spring Cloud Config Server: git 저장소를 기반으로 한 중앙 집중식 외부 구성 관리. 구성 리소스는 직접적으로 Spring Environment에 매핑되지만, 필요한 경우 Spring 애플리케이션이 아니어도 사용할 수 있습니다.
- Spring Cloud Netflix Eureka: Spring 애플리케이션과 다른 애플리케이션 모두에 의한 등록을 지원하는 서비스 등록 및 발견.
- Spring Cloud Gateway: 동적 라우팅, 모니터링, 복원력, 보안 등을 제공합니다.

### 구동 절차
1. ConfigServerApplication 실행
2. ServiceRegistryApplication 실행
3. GatewayApplication 실행
4. 그 외 MicroserviceApplication 실행

### TODO
- API 버저닝
- 도메인 엔티티 버저닝(동시성 제어 및 트랜잭션 고립 레벨 조절)
- 이벤트 소싱
- 메시시 큐잉(카프카 쓸 예정)
- 권한 체크
- 서킷 브레이커
- 분산 추적(Zipkin 쓸 예정)
- 이벤트 소싱(트랜잭션 제어)