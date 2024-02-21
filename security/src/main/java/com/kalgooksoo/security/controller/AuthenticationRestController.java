package com.kalgooksoo.security.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.kalgooksoo.security.client.UserClient;
import com.kalgooksoo.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthenticationRestController {

    private final UserClient userClient;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    @PostMapping("/access-token")
    public String authorize(String username, String password) {
        JsonNode jsonNode = userClient.signIn(username, password);

        // 200 인 경우
        // UsernamePasswordAuthenticationToken 객체를 생성합니다.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // AuthenticationManager를 사용하여 사용자를 인증하고 Authentication 객체를 얻습니다.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // JwtProvider를 사용하여 토큰을 생성합니다.
        String token = jwtProvider.generateToken(authentication);
        return token;
    }

}