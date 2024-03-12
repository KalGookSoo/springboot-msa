package com.kalgooksoo.board.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 카테고리 수정 커맨드
 */
@Schema(description = "카테고리 수정 커맨드")
public record UpdateCategoryCommand(
        
        @Parameter(description = "이름", schema = @Schema(description = "이름"), required = true)
        @NotBlank(message = "이름은 필수입니다")
        String name,

        @Parameter(description = "타입", schema = @Schema(description = "타입"), required = true)
        @NotBlank(message = "계정명은 필수입니다")
        @Size(min = 3, max = 20, message = "계정명은 3자 이상 20자 이하이어야 합니다")
        String type
        
) {

}