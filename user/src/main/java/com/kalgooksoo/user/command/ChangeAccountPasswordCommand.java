package com.kalgooksoo.user.command;

import com.kalgooksoo.user.annotation.PasswordsNotEqual;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 계정 패스워드 업데이트 명령에 대한 클래스입니다.
 */
@PasswordsNotEqual
public record ChangeAccountPasswordCommand(
        @NotNull @NotBlank String originPassword,
        @NotNull @NotBlank String newPassword
) {

}
