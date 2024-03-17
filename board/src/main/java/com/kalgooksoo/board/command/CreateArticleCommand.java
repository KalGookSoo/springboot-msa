package com.kalgooksoo.board.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 게시글 생성 커맨드
 *
 * @param title      제목
 * @param content    본문
 * @param categoryId 카테고리 식별자
 * @param createdBy  생성자
 */
@Schema(description = "게시글 생성 커맨드")
public record CreateArticleCommand(

        @Parameter(description = "제목. 이 필드는 필수입니다.", example = "제목", required = true)
        @Schema(description = "제목. 이 필드는 필수입니다.")
        @NotBlank(message = "제목은 필수입니다")
        @NotNull(message = "제목은 NULL이 될 수 없습니다")
        String title,

        @Parameter(description = "본문. 이 필드는 필수입니다.", example = "본문", required = true)
        @Schema(description = "본문. 이 필드는 필수입니다.")
        @NotBlank(message = "본문은 필수입니다")
        @NotNull(message = "본문은 NULL이 될 수 없습니다")
        String content,

        @Parameter(description = "카테고리 식별자. 이 필드는 필수입니다.", example = "698506bb-152f-462d-9d36-456c75a7848d", required = true)
        @Schema(description = "카테고리 식별자. 이 필드는 필수입니다.")
        @NotBlank(message = "카테고리 식별자는 필수입니다")
        @NotNull(message = "카테고리 식별자는 NULL이 될 수 없습니다")
        String categoryId,

        @Parameter(description = "생성자. 이 필드는 필수입니다.", example = "testuser", required = true)
        @Schema(description = "생성자. 이 필드는 필수입니다.")
        @NotBlank(message = "생성자는 필수입니다")
        @NotNull(message = "생성자는 NULL이 될 수 없습니다")
        String createdBy

) {
}
