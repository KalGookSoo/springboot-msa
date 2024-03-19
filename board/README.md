# Board Service
`board-service`는 게시판 정보를 관리하는 서비스입니다.

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
title: Board Service Domain
---
classDiagram
    direction TB
    class Article
    class Attachment
    class Category 
    class CategoryType 
    class Comment 
    class Hierarchical {
        <<abstract>>
    }
    class View
    class Vote
    class VoteType
    
    Hierarchical "1" -- "1" Category
    Hierarchical "1" -- "1" Comment
    Category "1" --> "N" Category
    Category "1" --> "N" Article
    Category "1" --> "1" CategoryType
    Article "1" --> "N" Comment
    Article "1" --> "N" Vote
    Article "1" --> "N" View
    Article "1" --> "N" Attachment
    Comment "1" --> "N" Comment
    Comment "1" --> "N" Vote
    Comment "1" --> "N" Attachment
    Vote "1" --> "1" VoteType

```

---

## Entity Relationship Diagram
```mermaid
---
title: Board Service ERD
---
erDiagram
    tb_category {
        string(36) id PK
        String(36) parent_id
        string name
        string type
        string created_by
        timestamp created_at
        timestamp modified_at
    }
    tb_article {
        string(36) id PK
        string(36) category_id FK
        string title
        text content
        string author
        timestamp created_at
        timestamp modified_at
    }
    tb_attachment {
        string(36) id PK
        string(36) reference_id
        string name
        string path_name
        string mime_type
        bigint size
        timestamp created_at
    }
    tb_comment {
        string(36) id PK
        string(36) article_id FK
        String(36) parent_id
        text content
        string author
        timestamp created_at
        timestamp modified_at
    }
    tb_vote {
        string(36) reference_id PK
        string voter PK
        string type
        string author
        timestamp voted_at
    }
    tb_view {
        string(36) reference_id PK
        string viewer PK
        timestamp viewed_at
    }
    tb_category ||--o{ tb_category : parent_id
    tb_category ||--o{ tb_article : category_id
    tb_article ||--o{ tb_attachment : reference_id
    tb_article ||--o{ tb_comment : article_id
    tb_article ||--o{ tb_vote : reference_id
    tb_article ||--o{ tb_view : reference_id
    tb_comment ||--o{ tb_comment : parent_id
    tb_comment ||--o{ tb_attachment : reference_id
    tb_comment ||--o{ tb_vote : reference_id
    tb_comment ||--o{ tb_view : reference_id
    
```

---



## Open API Specification
_애플리케이션 구동 후 /v3/api-docs.yaml로 요청을 하여 최신 OAS 문서를 다운로드 받을 수 있습니다._

[API 명세 바로가기](./docs%2Fboard-api-docs.yaml)

[스웨거 에디터 바로가기](https://editor.swagger.io/)