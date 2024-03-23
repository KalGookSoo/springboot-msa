package com.kalgooksoo.article.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 게시글 이동 커맨드
 *
 * @param categoryId 카테고리 식별자
 */
@Schema(description = "게시글 이동 커맨드")
public record MoveArticleCommand(

        @Parameter(description = "카테고리 식별자", required = true)
        @Schema(description = "카테고리 식별자. 이 필드는 필수입니다.", format = "uuid")
        @NotBlank(message = "카테고리 식별자는 필수입니다")
        @NotNull(message = "카테고리 식별자는 NULL이 될 수 없습니다")
        String categoryId

) {
}
