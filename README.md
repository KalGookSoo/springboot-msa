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

### install zipkin
```shell
docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin
```

### 데이터베이스 세팅
DBMS 벤더는 PostgreSQL을 사용합니다.<br>
테스트 환경은 H2DB를 사용합니다.<br>
테스트환경은 spring.profiles.active를 test로 할당합니다.
```sql
-- 데이터베이스 생성
CREATE DATABASE august;

-- 사용자 생성
CREATE USER kalgooksoo WITH PASSWORD '1234';

-- 데이터베이스에 대한 모든 권한 부여
GRANT ALL PRIVILEGES ON DATABASE august TO kalgooksoo;

-- august 데이터베이스 접속

-- 스키마 생성
CREATE SCHEMA springboot_msa;

-- 스키마에 대한 모든 권한 부여
GRANT ALL PRIVILEGES ON SCHEMA springboot_msa TO kalgooksoo;
```

### OpenAPI Specification

이 프로젝트는 OpenAPI Specification을 사용하여 API 문서를 자동으로 생성합니다.

- Swagger UI를 통해 API 문서를 실시간으로 확인하려면 웹 브라우저에서 `/swagger-ui/index.html` 경로로 접속합니다.
- OAS 3.0 형식의 API 문서는 `/v3/api-docs.yaml` 경로에서 파일을 다운로드할 수 있습니다.
