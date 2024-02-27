package com.kalgooksoo.security.service;

import org.springframework.security.core.Authentication;

/**
 * 인증 서비스
 */
public interface AuthenticationService {

    /**
     * 인증 주체를 반환합니다.
     *
     * @param username 계정명
     * @param password 패스워드
     * @return 인증 주체
     */
    Authentication authenticate(String username, String password);

    /**
     * 인증 주체를 반환합니다.
     *
     * @param token 토큰
     * @return 인증 주체
     */
    Authentication authenticate(String token);

    /**
     * 토큰을 생성합니다.
     *
     * @param authentication 인증 주체
     * @return 토큰
     */
    String generateToken(Authentication authentication);

    /**
     * 토큰을 갱신합니다.
     *
     * @param token 토큰
     * @return 갱신된 토큰
     */
    String refreshToken(String token);

}