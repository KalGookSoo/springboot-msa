package com.kalgooksoo.category.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 카테고리 생성 커맨드
 *
 * @param parentId  상위 카테고리 식별자
 * @param name      이름
 * @param type      타입
 */
@Schema(description = "카테고리 생성 커맨드")
public record CreateCategoryCommand(
        
        @Parameter(description = "상위 카테고리 식별자")
        @Schema(description = "상위 카테고리 식별자", format = "uuid")
        String parentId,

        @Parameter(description = "이름", required = true)
        @Schema(description = "이름", example = "카테고리")
        @NotBlank(message = "이름은 필수입니다")
        @NotNull(message = "이름은 NULL이 될 수 없습니다")
        String name,

        @Parameter(description = "타입", required = true)
        @Schema(description = "타입", example = "PUBLIC")
        @NotBlank(message = "계정명은 필수입니다")
        @Size(min = 3, max = 20, message = "계정명은 3자 이상 20자 이하이어야 합니다")
        @NotNull(message = "계정명은 NULL이 될 수 없습니다")
        String type

) {

}