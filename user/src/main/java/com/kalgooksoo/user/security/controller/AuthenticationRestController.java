package com.kalgooksoo.user.security.controller;

import com.kalgooksoo.user.security.jwt.JwtProvider;
import com.kalgooksoo.user.security.jwt.TokenModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

/**
 * 인증 REST 컨트롤러
 */
@Tag(name = "AuthenticationRestController", description = "인증 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final JwtProvider jwtProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Operation(parameters = {
            @Parameter(
                    in = ParameterIn.HEADER,
                    name = "Authorization",
                    required = true,
                    description = "Bearer ${token}",
                    schema = @Schema(type = "string")
            )
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 갱신 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @GetMapping("/token")
    public ResponseEntity<?> token(@RequestHeader String token) {
        // token으로 UserPrincipal을 반환합니다.
        if (jwtProvider.validateToken(token)) {
            Object principal = jwtProvider.getAuthentication(token).getPrincipal();
            return ResponseEntity.ok(principal);
//            return ResponseEntity.ok(userPrincipal);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // TODO 토큰 갱신
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenModel> refreshToken() {
        // TODO 토큰 갱신
        return null;
    }

    // TODO 토큰 기반 사용자 정보 조회

    // TODO sign-up
/**
 * /auth/token
 * /auth/token-refresh
 * /auth/token-info
 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
 */
}