package com.kalgooksoo.board.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 카테고리 이동 커맨드
 *
 * @param parentId
 */
@Schema(description = "카테고리 이동 커맨드")
public record MoveCategoryCommand(

        @Parameter(description = "상위 카테고리 식별자", schema = @Schema(description = "상위 카테고리 식별자"))
        String parentId

) {

}