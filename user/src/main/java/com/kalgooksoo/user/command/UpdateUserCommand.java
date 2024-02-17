package com.kalgooksoo.user.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 정보를 업데이트하는 데 사용되는 명령")
public record UpdateUserCommand(
        @Parameter(description = "이름. 이 필드는 필수입니다.", schema = @Schema(description = "이름. 이 필드는 필수입니다."))
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