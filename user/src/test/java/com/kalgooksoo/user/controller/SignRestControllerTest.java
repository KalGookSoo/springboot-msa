package com.kalgooksoo.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kalgooksoo.user.command.CreateUserCommand;
import com.kalgooksoo.user.command.SignInCommand;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 인증 REST 컨트롤러 테스트
 */
@Transactional
@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "spring.cloud.config.enabled=false"
})
@ActiveProfiles("test")
@AutoConfigureMockMvc
class SignRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private User testUser;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    @DisplayName("테스트 계정을 생성합니다.")
    void setup() throws UsernameAlreadyExistsException {
        UserRestController userRestController = new UserRestController(userService);
        CreateUserCommand command = new CreateUserCommand("tester", "12345678", "테스터", null, null, null, null, null);
        ResponseEntity<EntityModel<User>> responseEntity = userRestController.create(command);
        testUser = Objects.requireNonNull(responseEntity.getBody()).getContent();
    }

    @Test
    @DisplayName("사용자 로그인을 처리합니다. 성공 시 응답 코드 200을 반환합니다.")
    void signIn() throws Exception {
        // given
        SignInCommand command = new SignInCommand("tester", "12345678");

        // when
        mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("사용자 로그인을 처리합니다. 실패 시 응답 코드 400을 반환합니다.")
    void signInFail() throws Exception {
        // given
        SignInCommand command = new SignInCommand("tester", "invalidPassword");

        // when
        mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }

}