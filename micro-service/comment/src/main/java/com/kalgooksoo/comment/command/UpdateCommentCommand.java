package com.kalgooksoo.comment.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 댓글 수정 커맨드
 *
 * @param content 본문
 */
@Schema(description = "댓글 수정 커맨드")
public record UpdateCommentCommand(

        @Parameter(description = "본문", required = true)
        @Schema(description = "본문", example = "본문")
        @NotBlank(message = "본문은 필수입니다")
        @NotNull(message = "본문은 NULL이 될 수 없습니다")
        String content

) {
}
