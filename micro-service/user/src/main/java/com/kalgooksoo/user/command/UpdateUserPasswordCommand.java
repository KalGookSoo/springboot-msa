package com.kalgooksoo.user.command;

import com.kalgooksoo.user.annotation.PasswordsNotEqual;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 계정 패스워드 수정 명령
 */
@Schema(description = "계정 패스워드 수정 명령")
@PasswordsNotEqual
public record UpdateUserPasswordCommand(
        @Parameter(description = "현재 패스워드", required = true)
        @Schema(description = "현재 패스워드", example = "password")
        @NotNull(message = "현재 패스워드는 필수입니다.")
        @NotBlank(message = "현재 패스워드는 NULL이 될 수 없습니다.")
        String originPassword,

        @Parameter(description = "변경할 패스워드", required = true)
        @Schema(description = "변경할 패스워드", example = "newpassword")
        @NotNull(message = "변경할 패스워드는 필수입니다.")
        @NotBlank(message = "변경할 패스워드는 NULL이 될 수 없습니다.")
        String newPassword
) {}