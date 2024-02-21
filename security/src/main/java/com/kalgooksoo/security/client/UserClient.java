package com.kalgooksoo.security.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface UserClient {

    @PostExchange("/sign-in")
    JsonNode signIn(String username, String password);

}