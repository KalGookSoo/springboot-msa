package com.kalgooksoo.board.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 게시글 수정 커맨드
 *
 * @param title   제목
 * @param content 본문
 */
@Schema(description = "게시글 수정 커맨드")
public record UpdateArticleCommand(

        @Parameter(
                description = "제목. 이 필드는 필수입니다.",
                example = "제목",
                required = true,
                schema = @Schema(description = "제목. 이 필드는 필수입니다.")
        )
        @NotBlank(message = "제목은 필수입니다")
        @NotNull(message = "제목은 NULL이 될 수 없습니다")
        String title,

        @Parameter(
                description = "본문. 이 필드는 필수입니다.",
                example = "본문",
                required = true,
                schema = @Schema(description = "본문. 이 필드는 필수입니다.")
        )
        @NotBlank(message = "본문은 필수입니다")
        @NotNull(message = "본문은 NULL이 될 수 없습니다")
        String content

) {
}
