package com.kalgooksoo.menu.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 메뉴 생성 커맨드
 *
 * @param name      메뉴명
 * @param url       URL
 * @param parentId  부모 메뉴 ID
 * @param createdBy 생성자
 */
@Schema(description = "메뉴 생성 커맨드")
public record CreateMenuCommand(

        @Parameter(description = "메뉴명. 이 필드는 필수입니다.", schema = @Schema(description = "메뉴명. 이 필드는 필수입니다."), required = true)
        @NotBlank(message = "메뉴명은 필수입니다")
        @NotNull(message = "메뉴명은 null이 될 수 없습니다")
        String name,

        @Parameter(description = "URL. 이 필드는 필수입니다.", schema = @Schema(description = "URL. 이 필드는 필수입니다."), required = true)
        @NotBlank(message = "URL은 필수입니다")
        @NotNull(message = "URL은 null이 될 수 없습니다")
        String url,

        @Parameter(description = "부모 식별자", schema = @Schema(description = "부모 식별자"))
        String parentId,

        @Parameter(description = "생성자. 이 필드는 필수입니다.", schema = @Schema(description = "생성자. 이 필드는 필수입니다."), required = true)
        @NotBlank(message = "생성자는 필수입니다")
        @NotNull(message = "생성자는 null이 될 수 없습니다")
        String createdBy

) {}