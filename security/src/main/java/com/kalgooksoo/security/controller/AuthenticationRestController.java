package com.kalgooksoo.security.controller;

import com.kalgooksoo.security.command.SignInCommand;
import com.kalgooksoo.core.jwt.TokenModel;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
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

    @Operation(summary = "토큰 생성", description = "토큰을 생성합니다")
    @PostMapping("/token")
    public ResponseEntity<TokenModel> generateToken(
            @Parameter(schema = @Schema(implementation = SignInCommand.class)) @Valid @RequestBody SignInCommand command
    ) {
        Authentication authentication = authenticationService.authenticate(command);
        String jwt = authenticationService.generateToken(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(TokenModel.success(jwt));
    }

    @Operation(summary = "토큰 조회", description = "토큰을 조회합니다")
    @GetMapping("/token")
    public ResponseEntity<Authentication> findToken(
            @Parameter(in = ParameterIn.HEADER, description = "Bearer 토큰", required = true, schema = @Schema(type = "string", example = "Bearer {token}"))
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String bearerToken
    ) {
        String token;
        try {
            token = bearerToken.substring(7);
        } catch (StringIndexOutOfBoundsException e) {
            throw new AccessDeniedException("토큰이 유효하지 않습니다");
        }
        Authentication authentication = authenticationService.authenticate(token);
        return ResponseEntity.ok(authentication);
    }

    @PostMapping("/token-refresh")
    public ResponseEntity<TokenModel> refreshToken(
            @Parameter(in = ParameterIn.HEADER, description = "Bearer 토큰", required = true, schema = @Schema(type = "string", example = "Bearer {token}"))
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String bearerToken
    ) {
        String token;
        try {
            token = bearerToken.substring(7);
        } catch (StringIndexOutOfBoundsException e) {
            throw new AccessDeniedException("토큰이 유효하지 않습니다");
        }
        String generatedToken = authenticationService.refreshToken(token);
        return ResponseEntity.ok(TokenModel.success(generatedToken));
    }

}