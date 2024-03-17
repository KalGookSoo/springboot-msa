package com.kalgooksoo.board.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 투표 삭제 커맨드
 */
@Schema(description = "투표 삭제 커맨드")
public record DeleteVoteCommand(

        @Parameter(description = "참조 식별자", required = true)
        @Schema(description = "참조 식별자", format = "uuid")
        @NotBlank(message = "참조 식별자는 필수입니다")
        @NotNull(message = "참조 식별자는 NULL이 될 수 없습니다")
        String referenceId,

        @Parameter(description = "투표자", required = true)
        @Schema(description = "투표자", example = "testuser")
        @NotBlank(message = "투표자는 필수입니다")
        @NotNull(message = "투표자는 NULL이 될 수 없습니다")
        String voter

) {
}
