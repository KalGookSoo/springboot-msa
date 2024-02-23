package com.kalgooksoo.user.exception;

import lombok.Getter;

/**
 * 사용자명이 이미 존재하는 경우 예외
 */
@Getter
public class UsernameAlreadyExistsException extends Exception {

    private final String field = "username";

    private final String rejectedValue;

    public UsernameAlreadyExistsException(String rejectedValue, String message) {
        super(message);
        this.rejectedValue = rejectedValue;
    }

}