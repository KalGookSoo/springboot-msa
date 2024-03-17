 package com.kalgooksoo.user.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

 /**
  * 로그인 명령
  *
  * @param username 계정명
  * @param password 패스워드
  */
 @Schema(description = "로그인 명령")
 public record SignInCommand(

         @Parameter(description = "계정명", required = true)
         @Schema(description = "계정명", example = "testuser")
         @Size(min = 3, max = 20, message = "계정명은 3자 이상 20자 이하이어야 합니다")
         @NotBlank(message = "계정명은 필수입니다")
         @NotNull(message = "계정명은 null이 될 수 없습니다")
         String username,

         @Parameter(description = "패스워드", required = true)
         @Schema(description = "패스워드", example = "12341234")
         @Size(min = 8, message = "패스워드는 최소 8자 이상이어야 합니다")
         @NotBlank(message = "패스워드는 필수입니다")
         @NotNull(message = "패스워드는 null이 될 수 없습니다")
         String password

 ) {}