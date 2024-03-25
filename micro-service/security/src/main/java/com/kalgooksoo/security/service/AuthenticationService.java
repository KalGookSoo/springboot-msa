package com.kalgooksoo.security.service;

import com.kalgooksoo.security.command.SignInCommand;
import jakarta.annotation.Nonnull;
import org.springframework.security.core.Authentication;

/**
 * 인증 서비스
 */
public interface AuthenticationService {

    /**
     * 인증 주체를 반환합니다.
     *
     * @param command 인증 명령
     * @return 인증 주체
     */
    Authentication authenticate(@Nonnull SignInCommand command);

    /**
     * 인증 주체를 반환합니다.
     *
     * @param token 토큰
     * @return 인증 주체
     */
    Authentication authenticate(@Nonnull String token);

    /**
     * 토큰을 생성합니다.
     *
     * @param authentication 인증 주체
     * @return 토큰
     */
    String generateToken(@Nonnull Authentication authentication);

    /**
     * 토큰을 갱신합니다.
     *
     * @param token 토큰
     * @return 갱신된 토큰
     */
    String refreshToken(@Nonnull String token);

}