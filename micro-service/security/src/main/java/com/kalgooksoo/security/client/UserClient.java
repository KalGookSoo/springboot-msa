package com.kalgooksoo.security.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.kalgooksoo.security.command.SignInCommand;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

/**
 * 계정 서비스 클라이언트
 */
@HttpExchange
public interface UserClient {

    /**
     * 계정 검증
     *
     * @param command 계정 검증 명령
     * @return 응답 엔티티
     */
    @PostExchange("/users/sign-in")
    Mono<JsonNode> signIn(@Nonnull @RequestBody SignInCommand command);

}