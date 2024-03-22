package com.kalgooksoo.menu.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 메뉴 수정 커맨드
 *
 * @param name      메뉴명
 * @param url       URL
 */
@Schema(description = "메뉴 수정 커맨드")
public record UpdateMenuCommand(

        @Parameter(description = "메뉴명. 이 필드는 필수입니다.", required = true)
        @Schema(description = "메뉴명. 이 필드는 필수입니다.", example = "메뉴명")
        @NotBlank(message = "메뉴명은 필수입니다")
        @NotNull(message = "메뉴명은 null이 될 수 없습니다")
        String name,

        @Parameter(description = "URL. 이 필드는 필수입니다.", required = true)
        @Schema(description = "URL. 이 필드는 필수입니다.", example = "http://example.com")
        @NotBlank(message = "URL은 필수입니다")
        @NotNull(message = "URL은 null이 될 수 없습니다")
        String url

) {}