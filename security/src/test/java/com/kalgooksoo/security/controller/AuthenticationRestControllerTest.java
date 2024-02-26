package com.kalgooksoo.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kalgooksoo.security.command.SignInCommand;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 인증 REST 컨트롤러 테스트
 */
@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "spring.cloud.config.enabled=false"
})
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AuthenticationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setup() {
    }

    @Test
    @DisplayName("토큰을 생성합니다. 성공 시 응답 코드 200을 반환합니다.")
    void createToken() throws Exception {
        // Given
        SignInCommand command = new SignInCommand("tester", "12345678");

        // When
        mockMvc.perform(post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("토큰을 생성합니다. 실패 시 응답 코드 401을 반환합니다.")
    void createTokenFail() throws Exception {
        // Given
        SignInCommand command = new SignInCommand("tester", "12345679");

        // When
        String bearerToken = mockMvc.perform(post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getHeader(HttpHeaders.AUTHORIZATION);

        System.out.println("Bearer " + bearerToken);
    }

    @Test
    @DisplayName("사용자 인증 주체를 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    void getAuthentication() throws Exception {
        // Given
        SignInCommand command = new SignInCommand("tester", "12345678");

        String bearerToken = mockMvc.perform(post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andReturn().getResponse().getHeader(HttpHeaders.AUTHORIZATION);

        System.out.println("Bearer " + bearerToken);

        // When
        mockMvc.perform(get("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("사용자 인증 주체를 조회합니다. 실패 시 응답 코드 401을 반환합니다.")
    void getAuthenticationFail() throws Exception {
        // Given
        String invalidBearerToken = "invalidBearerToken";

        // When
        mockMvc.perform(get("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidBearerToken))
                .andExpect(status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }

}