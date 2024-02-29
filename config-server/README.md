# Config Server
- `config-server`는 외부 구성을 중앙 집중식으로 관리하는 서비스입니다.
- `config-server`는 `spring-cloud-config`로 구성하였습니다.
- `config-server`는 `git` 저장소 또는 파일 시스템을 기반으로 한 외부 구성을 제공합니다.

---

## 목차
1. [프로젝트 정보](#프로젝트-정보)

---

## 프로젝트 정보
![Language](https://img.shields.io/badge/language-Java-blue)
![Java Version](https://img.shields.io/badge/Java-17-blue)
![Build Tool](https://img.shields.io/badge/build%20tool-Gradle-orange)
![Spring Boot Version](https://img.shields.io/badge/Spring%20Boot-3.2.2-green)
![Spring Cloud Version](https://img.shields.io/badge/Spring%20Cloud-2023.0.0-green)
![License](https://img.shields.io/badge/license-Apache%202.0-brightgreen)

---


#### Config Server가 올바르게 동작하고 있는지 확인하는 방법
spring.application.name 과 spring.profiles.active 값을 조합하여 아래와 같은 URL로 접근한다.<br>
GET http://localhost:8888/config-server/default