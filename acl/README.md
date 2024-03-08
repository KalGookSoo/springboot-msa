# Acl Service
- `acl-service`는 접근제어목록 정보를 관리하는 서비스입니다.
- `acl-service`는 `Domain Object Security`(도메인 객체 보안)을 위한 서비스입니다.
- `acl-service`는 `Spring Security`의 `spring-security-acl`을 참조하여 구현되었습니다.
- `spring-security-acl`의 테이블 명세와 동일하도록 유지하였습니다.
  - 현 프로젝트의 네이밍 규칙과 `Primary Key`의 포맷이 다릅니다.
  - `spring-security-acl`로 빠르게 치환 가능하도록 하기 위함입니다.


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
    tb_acl_sid ||--o{ tb_acl_object_identity : owner_sid
    tb_acl_sid ||--o{ tb_acl_entry : sid
    tb_acl_class ||--o{ tb_acl_object_identity : object_id_class
    tb_acl_object_identity ||--o{ tb_acl_entry : acl_object_identity
    tb_acl_object_identity ||--o{ tb_acl_object_identity : parent_object

    tb_acl_sid {
        bigserial id PK
        boolean principal
        varchar(100) sid
    }

    tb_acl_class {
        bigserial id PK
        varchar(100) class
        varchar(100) class_id_type
    }

    tb_acl_object_identity {
        bigserial id PK
        bigint owner_sid FK
        bigint object_id_class FK
        varchar(36) object_id_identity
        bigint parent_object
        boolean entries_inheriting
    }

    tb_acl_entry {
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
_애플리케이션 구동 후 /v3/api-docs.yaml로 요청을 하여 최신 OAS 문서를 다운로드 받을 수 있습니다._

[API 명세 바로가기](./docs%2Facl-api-docs.yaml)

[스웨거 에디터 바로가기](https://editor.swagger.io/)