package com.kalgooksoo.user.exception;

public record ValidationError(String code, String message, String field, Object rejectedValue) {}