package com.kalgooksoo.vote.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 투표 생성 커맨드
 */
@Schema(description = "투표 생성 커맨드")
public record CreateVoteCommand(

        @Parameter(description = "참조 식별자", required = true)
        @Schema(description = "참조 식별자", format = "uuid")
        @NotBlank(message = "참조 식별자는 필수입니다")
        @NotNull(message = "참조 식별자는 NULL이 될 수 없습니다")
        String referenceId,

        @Parameter(description = "투표 유형", required = true)
        @Schema(description = "투표 유형", example = "APPROVE|DISAPPROVE")
        @NotBlank(message = "투표 유형은 필수입니다")
        @NotNull(message = "투표 유형은 NULL이 될 수 없습니다")
        String voteType

) {
}
