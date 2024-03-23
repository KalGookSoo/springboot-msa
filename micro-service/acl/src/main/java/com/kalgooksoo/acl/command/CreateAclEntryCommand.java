package com.kalgooksoo.acl.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@SuppressWarnings("ALL")
@Schema(description = "")
public record CreateAclEntryCommand(

        @Parameter(description = "클래스명. 이 필드는 필수입니다.", schema = @Schema(description = "클래스명. 이 필드는 필수입니다."), required = true)
        @NotBlank(message = "클래스명은 필수입니다")
        String className,

        @Parameter(description = "식별자. 이 필드는 필수입니다.", schema = @Schema(description = "식별자. 이 필드는 필수입니다."), required = true)
        @NotBlank(message = "식별자는 필수입니다")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$", message = "식별자는 UUID 포맷이어야 합니다")
        String identifier,

        @Parameter(description = "권한. 이 필드는 필수입니다.", schema = @Schema(description = "권한. 이 필드는 필수입니다."), required = true)
        @NotBlank(message = "권한은 필수입니다")
        String principal,

        @Parameter(description = "마스크. 이 필드는 필수입니다.", schema = @Schema(description = "마스크. 이 필드는 필수입니다."), required = true)
        @NotNull(message = "마스크는 필수입니다")
        @Min(value = 1, message = "마스크는 1, 2, 4, 8, 16 중 하나여야 합니다")
        @Max(value = 16, message = "마스크는 1, 2, 4, 8, 16 중 하나여야 합니다")
        Integer mask

) {
}