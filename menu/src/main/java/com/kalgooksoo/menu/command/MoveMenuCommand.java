package com.kalgooksoo.menu.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 메뉴 이동 커맨드
 *
 * @param parentId
 */
@Schema(description = "메뉴 이동 커맨드")
public record MoveMenuCommand(

        @Parameter(description = "상위 메뉴 식별자")
        @Schema(description = "상위 메뉴 식별자", format = "uuid")
        String parentId

) {

}