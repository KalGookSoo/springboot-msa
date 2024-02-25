package com.kalgooksoo.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kalgooksoo.user.command.CreateUserCommand;
import com.kalgooksoo.user.command.SignInCommand;
import com.kalgooksoo.user.command.UpdateUserCommand;
import com.kalgooksoo.user.command.UpdateUserPasswordCommand;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 계정 REST 컨트롤러 테스트
 */
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

    private User testUser;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    @DisplayName("테스트 계정을 생성합니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void setup() throws UsernameAlreadyExistsException {
        UserRestController userRestController = new UserRestController(userService);
        CreateUserCommand command = new CreateUserCommand("tester", "12345678", "테스터", null, null, null, null, null);
        ResponseEntity<EntityModel<User>> responseEntity = userRestController.create(command);
        testUser = Objects.requireNonNull(responseEntity.getBody()).getContent();
    }

    @Test
    @DisplayName("계정을 생성합니다. 성공 시 응답 코드 201을 반환합니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void createUserTest() throws Exception {
        // Given
        CreateUserCommand command = new CreateUserCommand("tester2", "12345678", "테스터2", null, null, null, null, null);

        // When
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("tester2"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정을 생성합니다. 실패 시 응답 코드 409을 반환합니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void createUserConflictTest() throws Exception {
        // Given
        CreateUserCommand command = new CreateUserCommand("tester", "12345678", "테스터2", null, null, null, null, null);

        // When
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정을 생성합니다. 실패 시 응답 코드 400을 반환합니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void createUserBadRequestTest() throws Exception {
        // Given
        CreateUserCommand command = new CreateUserCommand("tester2", null, null, null, null, null, null, null);

        // When
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정 목록을 조회합니다. 성공 시 응답 코드 200을 반환합니다. ")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void findAllTest() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정을 조회합니다. 조회된 건 수가 0 건인 경우 응답 본문 객체에 _embedded 프로퍼티가 없습니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void findAllEmptyTest() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/users")
                        .param("username", "not-exist-username"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").doesNotExist())
                .andDo(MockMvcResultHandlers.print());
    }

    // 계정 조회 시 200
    @Test
    @DisplayName("계정을 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void findByIdTest() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/users/{id}", testUser.getId()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    // 존재하지 않는 계정 조회 시 404
    @Test
    @DisplayName("계정을 조회합니다. 존재하지 않는 계정 조회 시 응답 코드 404을 반환합니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void findByIdNotFoundTest() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/users/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정을 수정합니다. 성공 시 응답 코드 200을 반환합니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void updateByIdTest() throws Exception {
        // Given
        UpdateUserCommand command = new UpdateUserCommand("테스터 업데이트", "updated", "email.com", "010", "1234", "5678");

        // When
        mockMvc.perform(put("/users/{id}", testUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정을 수정합니다. 실패 시 응답 코드 400을 반환합니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void updateByIdBadRequestTest() throws Exception {
        // Given
        UpdateUserCommand command = new UpdateUserCommand(null, "updated", "email.com", "010", "1234", "5678");

        // When
        mockMvc.perform(put("/users/{id}", testUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정을 수정합니다. 존재하지 않는 계정 수정 시 응답 코드 404을 반환합니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void updateByIdNotFoundTest() throws Exception {
        // Given
        UpdateUserCommand command = new UpdateUserCommand("테스터 업데이트", "updated", "email.com", "010", "1234", "5678");

        // When
        mockMvc.perform(put("/users/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정을 삭제합니다. 성공 시 응답 코드 204을 반환합니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void deleteByIdTest() throws Exception {
        // Given
        // When
        mockMvc.perform(delete("/users/{id}", testUser.getId()))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정을 삭제합니다. 존재하지 않는 계정 삭제 시 응답 코드 404을 반환합니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void deleteByIdNotFoundTest() throws Exception {
        // Given
        // When
        mockMvc.perform(delete("/users/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정 패스워드를 수정합니다. 성공 시 응답 코드 200을 반환합니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void updatePasswordTest() throws Exception {
        // Given
        UpdateUserPasswordCommand command = new UpdateUserPasswordCommand("12345678", "1234567890");

        // When
        mockMvc.perform(put("/users/{id}/password", testUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정 패스워드를 수정합니다. 실패 시 응답 코드 400을 반환합니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void updatePasswordBadRequestTest() throws Exception {
        // Given
        UpdateUserPasswordCommand command = new UpdateUserPasswordCommand("incorrectPassword", "1234567890");

        // When
        mockMvc.perform(put("/users/{id}/password", testUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("계정 패스워드를 수정합니다. 존재하지 않는 계정 패스워드 수정 시 응답 코드 404을 반환합니다.")
    @WithMockUser(username = "tester", roles = "ADMIN")
    void updatePasswordNotFoundTest() throws Exception {
        // Given
        UpdateUserPasswordCommand command = new UpdateUserPasswordCommand("12345678", "1234567890");

        // When
        mockMvc.perform(put("/users/{id}/password", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정명과 패스워드로 계정을 확인합니다. 성공 시 응답 코드 200을 반환합니다.")
    void verifyTest() throws Exception {
        // Given
        SignInCommand command = new SignInCommand("tester", "12345678");

        // When
        mockMvc.perform(post("/users/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("계정명과 패스워드로 계정을 확인합니다. 실패 시 응답 코드 400을 반환합니다.")
    void verifyBadRequestTest() throws Exception {
        // Given
        SignInCommand command = new SignInCommand("tester", "invalidPassword");

        // When
        mockMvc.perform(post("/users/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

}