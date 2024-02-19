package com.kalgooksoo.authorization.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
public class AuthorizationRestController {

    @PostMapping("/access-token")
    public String authorize() {
        return "Authorized";
    }

    @PostMapping("/refresh-token")
    public String refresh() {
        return "Refreshed";
    }

}