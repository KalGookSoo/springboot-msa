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

        @Parameter(
                description = "상위 메뉴 식별자",
                example = "698506bb-152f-462d-9d36-456c75a7848d",
                schema = @Schema(description = "상위 메뉴 식별자", implementation = String.class, format = "uuid")
        )
        String parentId

) {

}