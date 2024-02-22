package com.kalgooksoo.user.security;

import com.kalgooksoo.user.command.SignInCommand;
import com.kalgooksoo.user.domain.Authority;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.security.UserPrincipal;
import com.kalgooksoo.user.security.jwt.JwtProvider;
import com.kalgooksoo.user.security.jwt.TokenModel;
import com.kalgooksoo.user.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * 인증 REST 컨트롤러
 */
@Tag(name = "SignRestController", description = "인증 API")
@RestController
@RequiredArgsConstructor
public class SignRestController {

    private final JwtProvider jwtProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 사용자 인증
     *
     * @param command 인증 명령
     * @return 토큰 정보
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/sign-in")
    public ResponseEntity<TokenModel> signIn(@Valid @RequestBody SignInCommand command) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(command.username(), command.password());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(TokenModel.success(jwt));
    }

    // TODO 토큰 갱신

    // TODO 토큰 기반 사용자 정보 조회

    // TODO sign-up

}