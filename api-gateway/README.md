# API Gateway
- `api-gateway`는 클라이언트의 요청을 라우팅, 로드밸런싱, 필터링을 담당하는 서비스입니다.
- `api-gateway`는 `spring-cloud-gateway`로 구성하였습니다.
- 클라이언트는 `api-gateway`와 상호작용합니다.
- `api-gateway`는 `service-registry`에 등록된 `micro-service`에 라우팅합니다.
- `api-gateway`는 라우팅하기 전 인증 및 인가를 검증하여 필터링할 수 있습니다.

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

