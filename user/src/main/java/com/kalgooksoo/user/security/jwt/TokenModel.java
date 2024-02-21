package com.kalgooksoo.user.security.jwt;

import org.springframework.http.HttpStatus;

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