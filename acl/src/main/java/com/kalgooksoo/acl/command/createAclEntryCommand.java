package com.kalgooksoo.acl.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "")
public record createAclEntryCommand(

        @Parameter(description = "클래스명. 이 필드는 필수입니다.", schema = @Schema(description = "클래스명. 이 필드는 필수입니다."), required = true)
        @NotBlank(message = "클래스명은 필수입니다")
        String className,

        @Parameter(description = "식별자. 이 필드는 필수입니다.", schema = @Schema(description = "식별자. 이 필드는 필수입니다."), required = true)
        @NotBlank(message = "식별자는 필수입니다")
        String identifier,

        @Parameter(description = "권한. 이 필드는 필수입니다.", schema = @Schema(description = "권한. 이 필드는 필수입니다."), required = true)
        @NotBlank(message = "권한은 필수입니다")
        String principal,

        @Parameter(description = "마스크. 이 필드는 필수입니다.", schema = @Schema(description = "마스크. 이 필드는 필수입니다."), required = true)
        @NotBlank(message = "마스크는 필수입니다")
        int mask

) {
}