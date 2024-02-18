package com.kalgooksoo.user.exception;

import lombok.Getter;

@Getter
public class UsernameAlreadyExistsException extends RuntimeException {

    private final String field = "username";

    private final String rejectedValue;

    public UsernameAlreadyExistsException(String rejectedValue, String message) {
        super(message);
        this.rejectedValue = rejectedValue;
    }

}