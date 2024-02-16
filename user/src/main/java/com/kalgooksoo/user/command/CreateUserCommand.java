package com.kalgooksoo.user.command;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserCommand(
        @Parameter(required = true, description = "사용자 이름", example = "john_doe")
        @NotBlank(message = "사용자 이름은 필수입니다")
        @Size(min = 3, max = 20, message = "사용자 이름은 3자 이상 20자 이하이어야 합니다")
        String username,

        @Parameter(required = true, description = "패스워드", example = "password123")
        @NotBlank(message = "패스워드는 필수입니다")
        @Size(min = 8, message = "패스워드는 최소 8자 이상이어야 합니다")
        String password,

        @Parameter(required = true, description = "이름", example = "John Doe")
        @NotBlank(message = "이름은 필수입니다")
        String name,

        @Parameter(description = "이메일 ID", example = "user")
        String emailId,

        @Parameter(description = "이메일 도메인", example = "example.com")
        String emailDomain,

        @Parameter(description = "연락처 첫 번째 부분", example = "010")
        String firstContactNumber,

        @Parameter(description = "연락처 두 번째 부분", example = "1234")
        String middleContactNumber,

        @Parameter(description = "연락처 세 번째 부분", example = "5678")
        String lastContactNumber
) {}