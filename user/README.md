# USER-SERVICE

2. GET /users/{id} 회원 정보 조회
3. GET /users/{id}/authorities 회원 권한 조회

사용자 시나리오
1. POST /users CreateUserCommand 회원 가입
2. POST /auth/token SignInCommand 토큰 생성
3. GET /auth/token -H Authorization=Bearer {token} 사용자 인증 주체 조회