# Security Service
`security-service`는 사용자 인증 및 인가 부여를 담당하는 서비스입니다.

---

## 목차
1. [프로젝트 정보](#프로젝트-정보)
2. [토큰 발급 시나리오](#토큰-발급-시나리오)
3. [토큰 검증 시나리오](#토큰-검증-시나리오)
4. [API 명세서](#api-명세서)

---

## 프로젝트 정보
![Language](https://img.shields.io/badge/language-Java-blue)
![Java Version](https://img.shields.io/badge/Java-17-blue)
![Build Tool](https://img.shields.io/badge/build%20tool-Gradle-orange)
![Spring Boot Version](https://img.shields.io/badge/Spring%20Boot-3.2.2-green)
![Spring Cloud Version](https://img.shields.io/badge/Spring%20Cloud-2023.0.0-green)
![License](https://img.shields.io/badge/license-Apache%202.0-brightgreen)

---

## 토큰 발급 시나리오
```mermaid
sequenceDiagram
    participant Client as Client
    participant ApiGateway as API Gateway
    participant SecurityService as Security Service
    participant UserService as User Service

    Client->>ApiGateway: POST /auth/token
    ApiGateway->>SecurityService: POST /auth/token
    SecurityService->>UserService: POST /users/sign-in
    alt Successful Verification
        UserService->>SecurityService: 200 OK
        SecurityService->>ApiGateway: 200 OK
        ApiGateway->>Client: 200 OK, Return Token
    else Failed Verification
        UserService->>SecurityService: 400 Bad Request
        SecurityService->>ApiGateway: 401 Unauthorized
        ApiGateway->>Client: 401 Unauthorized
    end
```

---

## 토큰 검증 시나리오
```mermaid
sequenceDiagram
    participant Client as Client
    participant ApiGateway as API Gateway
    participant SecurityService as Security Service

    Client->>ApiGateway: GET /auth/token
    ApiGateway->>SecurityService: GET /auth/token
    alt Successful Verification
        SecurityService->>ApiGateway: 200 OK
        ApiGateway->>Client: 200 OK
    else Failed Verification
        SecurityService->>ApiGateway: 401 Unauthorized
        ApiGateway->>Client: 401 Unauthorized
    end
```

---

## API 명세서
[OpenAPI](docs%2Fsecurity-api-docs.yaml)