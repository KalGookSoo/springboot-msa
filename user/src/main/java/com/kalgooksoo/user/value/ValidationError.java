package com.kalgooksoo.user.value;

public record ValidationError(String code, String message, String field, Object rejectedValue) {}