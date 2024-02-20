package com.kalgooksoo.auth.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserClient {

    @GetExchange("/users?username={username}")
    JsonNode findByUsername(String username);

    @GetExchange("/users/{id}")
    JsonNode findById(String id);

}