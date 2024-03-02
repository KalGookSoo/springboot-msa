package com.kalgooksoo.core.jwt;

import org.springframework.http.HttpStatus;

/**
 * 토큰 모델
 *
 * @param httpStatus HTTP 상태
 * @param code       HTTP 상태 코드
 * @param message    메시지
 * @param data       데이터
 */
public record TokenModel(HttpStatus httpStatus, int code, String message, String data) {

    private static final String SUCCESS_MESSAGE = "Authentication successful";

    private static final String FAILURE_MESSAGE = "Authentication failed";

    public static TokenModel success(String token) {
        return new TokenModel(HttpStatus.OK, HttpStatus.OK.value(), SUCCESS_MESSAGE, token);
    }

    public static TokenModel failure() {
        return new TokenModel(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(), FAILURE_MESSAGE, null);
    }

}