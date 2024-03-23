package com.kalgooksoo.comment.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 댓글 생성 커맨드
 */
@Schema(description = "댓글 생성 커맨드")
public record CreateCommentCommand(

        @Parameter(description = "게시글 식별자", required = true)
        @Schema(description = "게시글 식별자", format = "uuid")
        @NotBlank(message = "게시글 식별자는 필수입니다")
        @NotNull(message = "게시글 식별자는 NULL이 될 수 없습니다")
        String articleId,

        @Parameter(description = "상위 댓글 식별자")
        @Schema(description = "상위 댓글 식별자", format = "uuid")
        String parentId,

        @Parameter(description = "본문", required = true)
        @Schema(description = "본문", example = "본문")
        @NotBlank(message = "본문은 필수입니다")
        @NotNull(message = "본문은 NULL이 될 수 없습니다")
        String content

) {
}
