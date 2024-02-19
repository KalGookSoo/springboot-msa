package com.kalgooksoo.user.exception;

import lombok.Getter;

@Getter
public class PasswordNotMatchException extends RuntimeException {

    private final String field = "originPassword";

    private final String rejectedValue;

    public PasswordNotMatchException(String rejectedValue, String message) {
        super(message);
        this.rejectedValue = rejectedValue;
    }

}