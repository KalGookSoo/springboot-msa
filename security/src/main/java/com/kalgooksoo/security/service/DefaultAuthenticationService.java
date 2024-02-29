package com.kalgooksoo.security.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.kalgooksoo.security.client.UserClient;
import com.kalgooksoo.security.command.SignInCommand;
import com.kalgooksoo.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.StreamSupport;

/**
 * @see AuthenticationService
 */
@Service
@RequiredArgsConstructor
public class DefaultAuthenticationService implements AuthenticationService {

    private final UserClient userClient;

    private final JwtProvider jwtProvider;

//    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public Authentication authenticate(SignInCommand command) {
        // TODO UserApplication의 POST /users/sign-in 엔드포인트를 호출하여 사용자 인증을 수행한다.
        JsonNode jsonNode = userClient.signIn(command).block();
        if (jsonNode != null) {
            JsonNode authorityNodes = jsonNode.get("authorities");
            Collection<SimpleGrantedAuthority> authorities = StreamSupport.stream(authorityNodes.spliterator(), false)
                    .map(node -> new SimpleGrantedAuthority(node.get("name").asText()))
                    .toList();

            return new UsernamePasswordAuthenticationToken(command.username(), command.password(), authorities);
        } else {
            throw new AccessDeniedException("사용자 인증에 실패했습니다");
        }
    }

    @Override
    public Authentication authenticate(String token) {
        if (jwtProvider.validateToken(token)) {
            return jwtProvider.getAuthentication(token);
        } else {
            throw new AccessDeniedException("토큰이 유효하지 않습니다");
        }
    }

    @Override
    public String generateToken(Authentication authentication) {
        return jwtProvider.generateToken(authentication);
    }

    @Override
    public String refreshToken(String token) {
        if (jwtProvider.validateToken(token)) {
            return jwtProvider.generateToken(jwtProvider.getAuthentication(token));
        } else {
            throw new AccessDeniedException("토큰이 유효하지 않습니다");
        }
    }

}