# Menu Service
`menu-service`는 메뉴 정보를 관리하는 서비스입니다.

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
title: Menu Service Domain
---
classDiagram
    direction TB
    class Menu
    class Hierarchical {
        <<abstract>>
    }
    Hierarchical "1" -- "1" Menu
    Menu "1" --> "N" Menu

```

---

## Entity Relationship Diagram
```mermaid
---
title: Menu Service ERD
---
erDiagram
    tb_menu {
        string(36) id PK
        string(36) parent_id
        string name
        string url
        timestamp created_at
        string created_by
        timestamp modified_at
    }
    tb_menu ||--o{ tb_menu : parent_id
```

---

## Open API Specification
_애플리케이션 구동 후 /v3/api-docs.yaml로 요청을 하여 최신 OAS 문서를 다운로드 받을 수 있습니다._

[API 명세 바로가기](./docs%2Fmenu-api-docs.yaml)

[스웨거 에디터 바로가기](https://editor.swagger.io/)