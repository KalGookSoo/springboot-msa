package com.kalgooksoo.user.command;

import jakarta.validation.constraints.NotBlank;

/**
 * 계정 업데이트 명령에 대한 클래스입니다.
 */
public record UpdateUserCommand(
        @NotBlank String name,
        String emailId,
        String emailDomain,
        String firstContactNumber,
        String middleContactNumber,
        String lastContactNumber
) {}