package com.kalgooksoo.user.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 계정 수정 명령
 *
 * @param name                이름
 * @param emailId             이메일 ID
 * @param emailDomain         이메일 도메인
 * @param firstContactNumber  첫 번째 연락처 번호
 * @param middleContactNumber 두 번째 연락처 번호
 * @param lastContactNumber   마지막 연락처 번호
 */
@Schema(description = "계정 수정 명령")
public record UpdateUserCommand(
        @Parameter(description = "이름", required = true)
        @Schema(description = "이름", example = "홍길동")
        @NotBlank(message = "이름은 필수입니다.")
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
) {}