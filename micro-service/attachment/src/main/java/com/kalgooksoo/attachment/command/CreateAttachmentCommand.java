package com.kalgooksoo.attachment.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 첨부파일 생성 커맨드
 *
 * @param referenceId 참조 식별자
 * @param name        이름
 * @param pathName    경로명
 * @param mimeType    MIME 타입
 * @param size        크기
 */
@Schema(description = "첨부파일 생성 커맨드")
public record CreateAttachmentCommand(

        @Parameter(description = "참조 식별자", required = true)
        @Schema(description = "참조 식별자", format = "uuid")
        @NotBlank(message = "참조 식별자는 필수입니다")
        @NotNull(message = "참조 식별자는 NULL이 될 수 없습니다")
        String referenceId,

        @Parameter(description = "이름", required = true)
        @Schema(description = "이름", example = "첨부파일")
        @NotBlank(message = "이름은 필수입니다")
        @NotNull(message = "이름은 NULL이 될 수 없습니다")
        String name,

        @Parameter(description = "경로명", required = true)
        @Schema(description = "경로명", example = "/path/to/file")
        @NotBlank(message = "경로명은 필수입니다")
        @NotNull(message = "경로명은 NULL이 될 수 없습니다")
        String pathName,

        @Parameter(description = "MIME 타입", required = true)
        @Schema(description = "MIME 타입", example = "application/octet-stream")
        @NotBlank(message = "MIME 타입은 필수입니다")
        @NotNull(message = "MIME 타입은 NULL이 될 수 없습니다")
        String mimeType,

        @Parameter(description = "크기", required = true)
        @Schema(description = "크기", example = "1024", implementation = Long.class)
        @Min(value = 1, message = "크기는 1 이상이어야 합니다")
        long size

) {
}
