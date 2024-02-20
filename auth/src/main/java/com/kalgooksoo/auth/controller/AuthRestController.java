package com.kalgooksoo.auth.controller;

import com.kalgooksoo.auth.component.JwtProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthRestController {


    private final JwtProvider jwtProvider;

    public AuthRestController(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/access-token")
    public String authorize() {
        return "Authorized";
    }

    @PostMapping("/refresh-token")
    public String refresh() {
        return "Refreshed";
    }

}