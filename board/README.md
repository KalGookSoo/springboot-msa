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
    class Category {
        -String id
        +create(): Category
        +update(): void
    }
    class Article {
        -String id
        -String categoryId
        +create(): Article
        +update(): void
        +increaseViews()
        +increaseLikes()
        +decreaseLikes()
        +increaseDislikes()
        +decreaseDislikes()
    }
    class Attachment {
        -String id
        -String articleId
        +create(): Attachment
        +update(): void
    }
    class Comment {
        -String id
        -String articleId
        +create(): Comment
        +update(): void
        +increaseLikes()
        +decreaseLikes()
        +increaseDislikes()
        +decreaseDislikes()
    }
    Category "1" -- "N" Article: has
    Article "1" -- "N" Attachment: has
    Article "1" -- "N" Comment: has
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
        integer views
        integer likes
        integer dislikes
        string created_by
        timestamp created_at
        timestamp modified_at
    }
    tb_attachment {
        string(36) id PK
        string(36) article_id FK
        string name
        string path_name
        string mime_type
        bigint size
        string created_by
        timestamp created_at
        timestamp modified_at
    }
    tb_comment {
        string(36) id PK
        string(36) article_id FK
        text content
        integer likes
        integer dislikes
        string created_by
        timestamp created_at
        timestamp modified_at
    }
    tb_article ||--o{ tb_attachment : article_id
    tb_article ||--o{ tb_comment : article_id
    tb_category ||--o{ tb_article : category_id
```

---



## Open API Specification
[API 명세 바로가기](docs%2Fboard-api-docs.yaml)