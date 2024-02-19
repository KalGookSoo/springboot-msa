# CONFIG-SERVER

### config-server가 올바르게 동작하고 있는지 확인하는 방법
spring.application.name 과 spring.profiles.active 값을 조합하여 아래와 같은 URL로 접근한다.<br>
GET http://localhost:8888/config-server/default