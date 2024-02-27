package com.kalgooksoo.security.controller;

import com.kalgooksoo.security.command.SignInCommand;
import com.kalgooksoo.security.jwt.TokenModel;
import com.kalgooksoo.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 인증 REST 컨트롤러
 */
@Tag(name = "AuthenticationRestController", description = "인증 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final AuthenticationService authenticationService;

    /**
     * 토큰 생성
     *
     * @param command 인증 명령
     * @return 토큰 정보
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 성공"),
            @ApiResponse(responseCode = "401", description = "잘못된 자격 증명")
    })
    @PostMapping("/token")
    public ResponseEntity<TokenModel> createToken(@Valid @RequestBody SignInCommand command) {
        Authentication authentication = authenticationService.authenticate(command.username(), command.password());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = authenticationService.generateToken(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(TokenModel.success(jwt));
    }


    /**
     * 사용자 인증 주체 조회
     *
     * @param bearerToken 헤더에 포함된 토큰
     * @return 토큰에 해당하는 사용자 정보
     */
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
            @ApiResponse(responseCode = "401", description = "잘못된 자격 증명")
    })
    @GetMapping("/token")
    public ResponseEntity<Authentication> findToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String bearerToken) {
        String token = bearerToken.substring(7);
        Authentication authentication = authenticationService.authenticate(token);
        return ResponseEntity.ok(authentication);
    }

    /**
     * 토큰 갱신
     *
     * @param bearerToken 헤더에 포함된 토큰
     * @return 갱신된 토큰 정보
     */
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
            @ApiResponse(responseCode = "401", description = "잘못된 자격 증명")
    })
    @PostMapping("/token-refresh")
    public ResponseEntity<TokenModel> refreshToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String bearerToken) {
        String token = bearerToken.substring(7);
        String generatedToken = authenticationService.refreshToken(token);
        return ResponseEntity.ok(TokenModel.success(generatedToken));
    }

}