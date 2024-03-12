package com.kalgooksoo.board.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 카테고리 생성 커맨드
 */
@Schema(description = "카테고리 생성 커맨드")
public record CreateCategoryCommand(
        
        @Parameter(description = "상위 카테고리 식별자", schema = @Schema(description = "상위 카테고리 식별자"))
        String parentId,

        @Parameter(description = "이름", schema = @Schema(description = "이름"), required = true)
        @NotBlank(message = "이름은 필수입니다")
        String name,

        @Parameter(description = "타입", schema = @Schema(description = "타입"), required = true)
        @NotBlank(message = "계정명은 필수입니다")
        @Size(min = 3, max = 20, message = "계정명은 3자 이상 20자 이하이어야 합니다")
        String type,

        @Parameter(description = "생성자", schema = @Schema(description = "생성자"), required = true)
        @NotBlank(message = "생성자는 필수입니다")
        String createdBy
        
) {

}