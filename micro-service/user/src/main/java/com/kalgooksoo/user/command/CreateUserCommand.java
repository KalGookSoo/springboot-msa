package com.kalgooksoo.user.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "계정 생성 명령")
public record CreateUserCommand(
        @Parameter(description = "계정명", required = true)
        @Schema(description = "계정명", example = "testuser")
        @NotBlank(message = "계정명은 필수입니다")
        @Size(min = 3, max = 20, message = "계정명은 3자 이상 20자 이하이어야 합니다")
        String username,

        @Parameter(description = "패스워드", required = true)
        @Schema(description = "패스워드", example = "password")
        @NotBlank(message = "패스워드는 필수입니다")
        @Size(min = 8, message = "패스워드는 최소 8자 이상이어야 합니다")
        String password,

        @Parameter(description = "이름", required = true)
        @Schema(description = "이름", example = "홍길동")
        @NotBlank(message = "이름은 필수입니다")
        @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하이어야 합니다")
        String name,

        @Parameter(description = "이메일 ID")
        @Schema(description = "이메일 ID", example = "testuser")
        String emailId,

        @Parameter(description = "이메일 도메인")
        @Schema(description = "이메일 도메인", example = "kalgooksoo.com")
        String emailDomain,

        @Parameter(description = "첫 번째 연락처 번호")
        @Schema(description = "첫 번째 연락처 번호", example = "010")
        String firstContactNumber,

        @Parameter(description = "두 번째 연락처 번호")
        @Schema(description = "두 번째 연락처 번호", example = "1234")
        String middleContactNumber,

        @Parameter(description = "마지막 연락처 번호")
        @Schema(description = "마지막 연락처 번호", example = "5678")
        String lastContactNumber
) {
}