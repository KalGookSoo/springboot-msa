package com.kalgooksoo.user.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "계정을 생성하는 데 사용되는 명령")
public record CreateUserCommand(
        @Parameter(description = "계정명. 이 필드는 필수입니다.", schema = @Schema(description = "계정명. 이 필드는 필수입니다."))
        @NotBlank(message = "계정명은 필수입니다")
        @Size(min = 3, max = 20, message = "계정명은 3자 이상 20자 이하이어야 합니다")
        String username,

        @Parameter(description = "패스워드. 이 필드는 필수입니다.", schema = @Schema(description = "패스워드. 이 필드는 필수입니다."))
        @NotBlank(message = "패스워드는 필수입니다")
        @Size(min = 8, message = "패스워드는 최소 8자 이상이어야 합니다")
        String password,

        @Parameter(description = "이름. 이 필드는 선택 사항입니다.", schema = @Schema(description = "이름. 이 필드는 선택 사항입니다."))
        String name,

        @Parameter(description = "이메일 ID. 이 필드는 선택 사항입니다.", schema = @Schema(description = "이메일 ID. 이 필드는 선택 사항입니다."))
        String emailId,

        @Parameter(description = "이메일 도메인. 이 필드는 선택 사항입니다.", schema = @Schema(description = "이메일 도메인. 이 필드는 선택 사항입니다."))
        String emailDomain,

        @Parameter(description = "첫 번째 연락처 번호. 이 필드는 선택 사항입니다.", schema = @Schema(description = "첫 번째 연락처 번호. 이 필드는 선택 사항입니다."))
        String firstContactNumber,

        @Parameter(description = "두 번째 연락처 번호. 이 필드는 선택 사항입니다.", schema = @Schema(description = "두 번째 연락처 번호. 이 필드는 선택 사항입니다."))
        String middleContactNumber,

        @Parameter(description = "마지막 연락처 번호. 이 필드는 선택 사항입니다.", schema = @Schema(description = "마지막 연락처 번호. 이 필드는 선택 사항입니다."))
        String lastContactNumber
) {}