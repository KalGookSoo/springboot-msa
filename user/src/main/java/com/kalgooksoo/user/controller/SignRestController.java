package com.kalgooksoo.user.controller;

import com.kalgooksoo.user.command.SignInCommand;
import com.kalgooksoo.user.domain.Authority;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.model.UserPrincipal;
import com.kalgooksoo.user.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    private final UserService userService;

    /**
     * 사용자 로그인을 처리합니다.
     *
     * @param command 로그인 명령
     * @return 사용자 정보
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInCommand command) {
        User user = userService.verify(command.username(), command.password());
        Set<Authority> authorities = new HashSet<>(userService.findAuthoritiesByUserId(user.getId()));
        UserPrincipal userPrincipal = UserPrincipal.create(user, authorities);
        return ResponseEntity.ok(userPrincipal);
    }

}