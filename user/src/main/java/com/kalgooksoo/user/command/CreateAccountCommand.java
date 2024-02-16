package com.kalgooksoo.user.command;

import com.kalgooksoo.user.value.ContactNumber;
import com.kalgooksoo.user.value.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 계정 생성 명령에 대한 클래스입니다.
 */
public record CreateAccountCommand(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String name,
        Email email,
        ContactNumber contactNumber
) {}