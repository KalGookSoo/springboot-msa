package com.kalgooksoo.category.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 카테고리 이동 커맨드
 *
 * @param parentId
 */
@Schema(description = "카테고리 이동 커맨드")
public record MoveCategoryCommand(

        @Parameter(description = "상위 카테고리 식별자", required = true)
        @Schema(description = "상위 카테고리 식별자", format = "uuid")
        @NotBlank(message = "상위 카테고리 식별자는 필수입니다")
        @NotNull(message = "상위 카테고리 식별자는 null이 될 수 없습니다")
        String parentId

) {

}