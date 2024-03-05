package com.kalgooksoo.user.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kalgooksoo.exception.ExceptionHandlingController;
import com.kalgooksoo.user.command.CreateUserCommand;
import com.kalgooksoo.user.command.SignInCommand;
import com.kalgooksoo.user.command.UpdateUserCommand;
import com.kalgooksoo.user.command.UpdateUserPasswordCommand;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.repository.AuthorityJpaRepository;
import com.kalgooksoo.user.repository.AuthorityRepository;
import com.kalgooksoo.user.repository.UserJpaRepository;
import com.kalgooksoo.user.repository.UserRepository;
import com.kalgooksoo.user.service.DefaultUserService;
import com.kalgooksoo.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 계정 REST 컨트롤러 테스트
 */
@DataJpaTest
@ActiveProfiles("test")
class UserRestControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager entityManager;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setup() {
        UserRepository userRepository = new UserJpaRepository(entityManager.getEntityManager());
        AuthorityRepository authorityRepository = new AuthorityJpaRepository(entityManager.getEntityManager());
        UserService userService = new DefaultUserService(userRepository, authorityRepository);
        UserRestController userRestController = new UserRestController(userService);
        ExceptionHandlingController exceptionHandlingController = new ExceptionHandlingController();
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController, exceptionHandlingController).build();
    }

    @Test
    @DisplayName("계정을 생성합니다. 성공 시 응답 코드 201을 반환합니다.")
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
    void findAllTest() throws Exception {
        // Given
        CreateUserCommand command = new CreateUserCommand("tester2", "12345678", "테스터2", null, null, null, null, null);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("tester2"))
                .andDo(MockMvcResultHandlers.print());

        // When
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정을 조회합니다. 조회된 건 수가 0 건인 경우 응답 본문 객체에 _embedded 프로퍼티가 없습니다.")
    void findAllEmptyTest() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/users")
                        .param("username", "not-exist-username"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").doesNotExist())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정을 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    void findByIdTest() throws Exception {
        // Given
        CreateUserCommand command = new CreateUserCommand("tester2", "12345678", "테스터2", null, null, null, null, null);
        MockHttpServletResponse httpServletResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("tester2"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        EntityModel<User> entityModel = mapper.readValue(httpServletResponse.getContentAsString(), new TypeReference<>() {});
        User user = entityModel.getContent();
        Assertions.assertThat(user).isNotNull();

        // When
        mockMvc.perform(get("/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    // 존재하지 않는 계정 조회 시 404
    @Test
    @DisplayName("계정을 조회합니다. 존재하지 않는 계정 조회 시 응답 코드 404을 반환합니다.")
    void findByIdNotFoundTest() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/users/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정을 수정합니다. 성공 시 응답 코드 200을 반환합니다.")
    void updateByIdTest() throws Exception {
        // Given
        CreateUserCommand command = new CreateUserCommand("tester2", "12345678", "테스터2", null, null, null, null, null);
        MockHttpServletResponse httpServletResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("tester2"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        EntityModel<User> entityModel = mapper.readValue(httpServletResponse.getContentAsString(), new TypeReference<>() {});
        User user = entityModel.getContent();
        Assertions.assertThat(user).isNotNull();

        UpdateUserCommand updateUserCommand = new UpdateUserCommand("테스터 업데이트", "updated", "email.com", "010", "1234", "5678");


        // When
        mockMvc.perform(put("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateUserCommand)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정을 수정합니다. 실패 시 응답 코드 400을 반환합니다.")
    void updateByIdBadRequestTest() throws Exception {
        // Given
        CreateUserCommand command = new CreateUserCommand("tester2", "12345678", "테스터2", null, null, null, null, null);
        MockHttpServletResponse httpServletResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("tester2"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        EntityModel<User> entityModel = mapper.readValue(httpServletResponse.getContentAsString(), new TypeReference<>() {});
        User user = entityModel.getContent();
        Assertions.assertThat(user).isNotNull();

        UpdateUserCommand updateUserCommand = new UpdateUserCommand(null, "updated", "email.com", "010", "1234", "5678");

        // When
        mockMvc.perform(put("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateUserCommand)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정을 수정합니다. 존재하지 않는 계정 수정 시 응답 코드 404을 반환합니다.")
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
    void deleteByIdTest() throws Exception {
        // Given
        CreateUserCommand command = new CreateUserCommand("tester2", "12345678", "테스터2", null, null, null, null, null);
        MockHttpServletResponse httpServletResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("tester2"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        EntityModel<User> entityModel = mapper.readValue(httpServletResponse.getContentAsString(), new TypeReference<>() {});
        User user = entityModel.getContent();
        Assertions.assertThat(user).isNotNull();

        // When
        mockMvc.perform(delete("/users/{id}", user.getId()))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정을 삭제합니다. 존재하지 않는 계정 삭제 시 응답 코드 404을 반환합니다.")
    void deleteByIdNotFoundTest() throws Exception {
        // Given
        // When
        mockMvc.perform(delete("/users/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정 패스워드를 수정합니다. 성공 시 응답 코드 200을 반환합니다.")
    void updatePasswordTest() throws Exception {
        // Given
        CreateUserCommand command = new CreateUserCommand("tester2", "12345678", "테스터2", null, null, null, null, null);
        MockHttpServletResponse httpServletResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("tester2"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        EntityModel<User> entityModel = mapper.readValue(httpServletResponse.getContentAsString(), new TypeReference<>() {});
        User user = entityModel.getContent();
        Assertions.assertThat(user).isNotNull();

        UpdateUserPasswordCommand updateUserPasswordCommand = new UpdateUserPasswordCommand("12345678", "1234567890");

        // When
        mockMvc.perform(put("/users/{id}/password", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateUserPasswordCommand)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계정 패스워드를 수정합니다. 실패 시 응답 코드 400을 반환합니다.")
    void updatePasswordBadRequestTest() throws Exception {
        // Given
        CreateUserCommand command = new CreateUserCommand("tester2", "12345678", "테스터2", null, null, null, null, null);
        MockHttpServletResponse httpServletResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("tester2"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        EntityModel<User> entityModel = mapper.readValue(httpServletResponse.getContentAsString(), new TypeReference<>() {});
        User user = entityModel.getContent();
        Assertions.assertThat(user).isNotNull();

        UpdateUserPasswordCommand updateUserPasswordCommand = new UpdateUserPasswordCommand("incorrectPassword", "1234567890");

        // When
        mockMvc.perform(put("/users/{id}/password", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateUserPasswordCommand)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("계정 패스워드를 수정합니다. 존재하지 않는 계정 패스워드 수정 시 응답 코드 404을 반환합니다.")
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
        CreateUserCommand command = new CreateUserCommand("tester2", "12345678", "테스터2", null, null, null, null, null);
        MockHttpServletResponse httpServletResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("tester2"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        EntityModel<User> entityModel = mapper.readValue(httpServletResponse.getContentAsString(), new TypeReference<>() {});
        User user = entityModel.getContent();
        Assertions.assertThat(user).isNotNull();

        SignInCommand signInCommand = new SignInCommand("tester", "12345678");

        // When
        mockMvc.perform(post("/users/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signInCommand)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("계정명과 패스워드로 계정을 확인합니다. 실패 시 응답 코드 400을 반환합니다.")
    void verifyBadRequestTest() throws Exception {
        // Given
        CreateUserCommand command = new CreateUserCommand("tester2", "12345678", "테스터2", null, null, null, null, null);
        MockHttpServletResponse httpServletResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("tester2"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        EntityModel<User> entityModel = mapper.readValue(httpServletResponse.getContentAsString(), new TypeReference<>() {});
        User user = entityModel.getContent();
        Assertions.assertThat(user).isNotNull();

        SignInCommand signInCommand = new SignInCommand("tester", "invalidPassword");

        // When
        mockMvc.perform(post("/users/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signInCommand)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

}