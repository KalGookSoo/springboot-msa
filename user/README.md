# User Service
`user-service`는 사용자 정보를 관리하는 서비스입니다.

---

## 목차
1. [프로젝트 정보](#프로젝트-정보)  
2. [Domain](#domain)
3. [Entity Relationship Diagram](#entity-relationship-diagram)
3. [Open API Specification](#open-api-specification)

---

## 프로젝트 정보
![Language](https://img.shields.io/badge/language-Java-blue)
![Java Version](https://img.shields.io/badge/Java-17-blue)
![Build Tool](https://img.shields.io/badge/build%20tool-Gradle-orange)
![Spring Boot Version](https://img.shields.io/badge/Spring%20Boot-3.2.2-green)
![Spring Cloud Version](https://img.shields.io/badge/Spring%20Cloud-2023.0.0-green)
![License](https://img.shields.io/badge/license-Apache%202.0-brightgreen)

---

## Domain

```mermaid
---
title: User Service Domain
---
classDiagram
    class User {
        -String id
        -Email email
        -ContactNumber contactNumber
        +create(): User
        +update(): void
        +changePassword(): void
        +isAccountNonExpired(): boolean
        +isAccountNonLocked(): boolean
        +isCredentialsNonExpired(): boolean
        +isEnabled(): boolean
    }
    class Email {
        -String id
        -String domain
        +value()
    }
    class ContactNumber {
        -String first
        -String middle
        -String last
        +value()
    }
    class Authority {
        -String id
        -String userId
        +create(): Authority
    }
    User "1" -- "N" Authority: has
    User "1" -- "1" Email: has
    User "1" -- "1" ContactNumber: has
```

---

## Entity Relationship Diagram
```mermaid
---
title: User Service ERD
---
erDiagram
    tb_user {
        string(36) id PK
        string first_contact_number
        string middle_contact_number
        string last_contact_number
        timestamp created_at
        timestamp credentials_expired_at
        string email_domain
        string email_id
        timestamp expired_at
        timestamp locked_at
        timestamp modified_at
        string name
        string password
        string username
    }
    tb_authority {
        string(36) id PK
        string name
        string(36) user_id FK
    }
    tb_user ||--o{ tb_authority: user_id
```
---

## Open API Specification
_애플리케이션 구동 후 /v3/api-docs.yaml로 요청을 하여 최신 OAS 문서를 다운로드 받을 수 있습니다._

[API 명세 바로가기](./docs%2Fuser-api-docs.yaml)

[스웨거 에디터 바로가기](https://editor.swagger.io/)