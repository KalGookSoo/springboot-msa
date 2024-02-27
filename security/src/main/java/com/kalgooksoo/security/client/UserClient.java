package com.kalgooksoo.security.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * 계정 서비스 클라이언트
 */
@HttpExchange
public interface UserClient {

    /**
     * 계정 검증
     *
     * @param username 계정명
     * @param password 패스워드
     * @return 응답 엔티티
     */
    @PostExchange("/users/sign-in")
    ResponseEntity<JsonNode> signIn(String username, String password);

}