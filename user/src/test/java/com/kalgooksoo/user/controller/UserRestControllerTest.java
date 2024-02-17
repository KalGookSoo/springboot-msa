package com.kalgooksoo.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kalgooksoo.user.command.CreateUserCommand;
import com.kalgooksoo.user.domain.User;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "spring.cloud.config.enabled=false"
})
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private UserRestController userRestController;

    private User testUser;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // 셋업 시 테스트 계정을 생성하여 testUser에 할당
    @BeforeEach
    void setup() {
        userRestController = new UserRestController(userService);
        CreateUserCommand command = new CreateUserCommand("tester", "12345678", "테스터", null, null, null, null, null);
        ResponseEntity<EntityModel<User>> responseEntity = userRestController.create(command);
        testUser = Objects.requireNonNull(responseEntity.getBody()).getContent();
    }

    // 계정 생성 후 코드가 201인지 확인
    @Test
    @DisplayName("계정을 생성합니다. 성공 시 응답 코드 201을 반환합니다.")
    void createUserTest() throws Exception {
        // Given
        User account = User.create("tester2", "12345678", "테스터2", null, null);

        // When
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(account)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.username").value("tester2"))
                .andDo(MockMvcResultHandlers.print());


    }

    // 계정 생성 시 username이 중복되었을 경우 409인지 확인

    // 계정 생성 시 username이 null인 경우, 또는 password가 8자리 미만일 경우 400인지 확인

    // 계정 목록 조회 시

    // 계정 조회 시 200이며 식별자가 있는지 확인

    // 존재하지 않는 계정 조회 시 404인지 확인(경로 파라미터를 랜덤 UUID 값으로 조회)



}