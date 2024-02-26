# SECURITY-SERVICE API

## 토큰 생성

**POST** `/auth/token`

이 API는 새로운 토큰을 생성합니다.

---

## 인증 주체 조회

**GET** `/auth/token`

이 API는 토큰에 대응되는 인증 주체를 반환합니다.

---

## 토큰 갱신

**POST** `/auth/token-refresh`

이 API는 토큰을 갱신하여 반환합니다.

---

## 토큰 발급 시나리오

```mermaid
sequenceDiagram
    participant Client as Client
    participant SecurityService as SECURITY-SERVICE
    participant UserService as USER-SERVICE

    Client->>SecurityService: POST /auth/token (username, password)
    SecurityService->>UserService: POST /users/sign-in (username, password)
    alt Successful Verification
        UserService->>SecurityService: 200 OK
        SecurityService->>Client: 200 OK, Return Token
    else Failed Verification
        UserService->>SecurityService: 400 Bad Request
        SecurityService->>Client: 400 Bad Request
    end
```