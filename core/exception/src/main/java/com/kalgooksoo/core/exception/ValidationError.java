package com.kalgooksoo.core.exception;

public record ValidationError(String code, String message, String field, Object rejectedValue) {}