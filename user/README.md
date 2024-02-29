# USER-SERVICE
`USER-SERVICE`는 사용자 정보를 관리하는 서비스입니다.

---

## 목차
1. [프로젝트 정보](#프로젝트-정보)  
2. [도메인 엔티티](#도메인-엔티티)
3. [API 명세서](#api-명세서)

---

## 프로젝트 정보
- **프로그래밍 언어**: Java
- **Java 버전**: 17
- **빌드 도구**: Gradle
- **스프링 부트 버전**: 3.2.2
- **스프링 클라우드 버전**: 2023.0.0

---

## 도메인 엔티티
```mermaid
classDiagram
    class User {
        -String id
        +create()
        +update()
        +changePassword()
        +isAccountNonExpired()
        +isAccountNonLocked()
        +isCredentialsNonExpired()
        +isEnabled()
    }
    class Authority {
        -String id
        -String userId
        +create()
    }
    User "1" -- "N" Authority : has
```

---

## API 명세서
[OpenAPI](docs%2Fuser-api-docs.yaml)

---