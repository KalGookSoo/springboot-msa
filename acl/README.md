# Acl Service
`acl-service`는 접근제어목록 정보를 관리하는 서비스입니다.

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

```

---

## Entity Relationship Diagram
```mermaid
---
title: Acl Service ERD
---
erDiagram
    acl_sid ||--o{ acl_object_identity : owner_sid
    acl_sid ||--o{ acl_entry : sid
    acl_class ||--o{ acl_object_identity : object_id_class
    acl_object_identity ||--o{ acl_entry : acl_object_identity
    acl_object_identity ||--o{ acl_object_identity : parent_object

    acl_sid {
        bigserial id PK
        boolean principal
        varchar(100) sid
    }

    acl_class {
        bigserial id PK
        varchar(100) class
        varchar(100) class_id_type
    }

    acl_object_identity {
        bigserial id PK
        bigint owner_sid FK
        bigint object_id_class FK
        varchar(36) object_id_identity
        bigint parent_object
        boolean entries_inheriting
    }

    acl_entry {
        bigserial id PK
        bigint acl_object_identity FK
        bigint sid FK
        int ace_order
        integer mask
        boolean granting
        boolean audit_success
        boolean audit_failure
    }
```


---



## Open API Specification
[API 명세 바로가기](docs%2Facl-api-docs.yaml)