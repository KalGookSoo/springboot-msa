package com.kalgooksoo.user.security.controller;

import com.kalgooksoo.user.command.CreateUserCommand;
import com.kalgooksoo.user.command.SignInCommand;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.security.service.DefaultUserDetailsService;
import com.kalgooksoo.user.security.jwt.JwtProvider;
import com.kalgooksoo.user.security.jwt.TokenModel;
import com.kalgooksoo.user.value.ContactNumber;
import com.kalgooksoo.user.value.Email;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * 사인 REST 컨트롤러
 */
@Tag(name = "SignRestController", description = "사인 API")
@RestController
@RequiredArgsConstructor
public class SignRestController {

    private final DefaultUserDetailsService userDetailsService;

    private final JwtProvider jwtProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 사용자 등록
     *
     * @param command 사용자 등록 명령
     * @return 사용자 인증 주체
     * @throws UsernameAlreadyExistsException 사용자명이 이미 존재하는 경우
     */
    @PostMapping("/sign-up")
    public ResponseEntity<UserDetails> signUp(@Valid @RequestBody CreateUserCommand command) throws UsernameAlreadyExistsException {

        // Create user
        Email email = new Email(command.emailId(), command.emailDomain());
        ContactNumber contactNumber = new ContactNumber(command.firstContactNumber(), command.middleContactNumber(), command.lastContactNumber());
        User user = User.create(command.username(), command.password(), command.name(), email, contactNumber);
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.signUp(user);
        } catch (UsernameAlreadyExistsException e) {
            throw new UsernameAlreadyExistsException(command.username(), "계정이 이미 존재합니다");
        }

        // Create token
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(command.username(), command.password());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        String jwt = jwtProvider.generateToken(authentication);

        // Create response headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(userDetails);
    }

    /**
     * 사용자 인증 및 토큰 생성
     *
     * @param command 인증 명령
     * @return 토큰 정보
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 성공"),
            @ApiResponse(responseCode = "401", description = "잘못된 자격 증명")
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

}