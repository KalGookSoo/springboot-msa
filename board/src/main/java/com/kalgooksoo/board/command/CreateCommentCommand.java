package com.kalgooksoo.board.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 댓글 생성 커맨드
 */
@Schema(description = "댓글 생성 커맨드")
public record CreateCommentCommand(

        @Parameter(
                description = "게시글 식별자",
                example = "698506bb-152f-462d-9d36-456c75a7848d",
                required = true,
                schema = @Schema(description = "게시글 식별자", implementation = String.class, format = "uuid")
        )
        @NotBlank(message = "게시글 식별자는 필수입니다")
        @NotNull(message = "게시글 식별자는 NULL이 될 수 없습니다")
        String articleId,

        @Parameter(
                description = "상위 댓글 식별자",
                example = "698506bb-152f-462d-9d36-456c75a7848d",
                schema = @Schema(description = "상위 댓글 식별자", implementation = String.class, format = "uuid")
        )
        String parentId,

        @Parameter(
                description = "본문",
                example = "본문",
                required = true,
                schema = @Schema(description = "본문", implementation = String.class)
        )
        @NotBlank(message = "본문은 필수입니다")
        @NotNull(message = "본문은 NULL이 될 수 없습니다")
        String content,

        @Parameter(
                description = "작성자",
                example = "testuser",
                required = true,
                schema = @Schema(description = "작성자", implementation = String.class)
        )
        @NotBlank(message = "작성자는 필수입니다")
        @NotNull(message = "작성자는 NULL이 될 수 없습니다")
        String author

) {
}
