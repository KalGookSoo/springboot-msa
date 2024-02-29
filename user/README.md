# User Service
`user-service`는 사용자 정보를 관리하는 서비스입니다.

---

## 목차
1. [프로젝트 정보](#프로젝트-정보)  
2. [도메인 엔티티](#도메인-엔티티)
3. [API 명세서](#api-명세서)

---

## 프로젝트 정보
![Language](https://img.shields.io/badge/language-Java-blue)
![Java Version](https://img.shields.io/badge/Java-17-blue)
![Build Tool](https://img.shields.io/badge/build%20tool-Gradle-orange)
![Spring Boot Version](https://img.shields.io/badge/Spring%20Boot-3.2.2-green)
![Spring Cloud Version](https://img.shields.io/badge/Spring%20Cloud-2023.0.0-green)
![License](https://img.shields.io/badge/license-Apache%202.0-brightgreen)

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