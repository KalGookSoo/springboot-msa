package com.kalgooksoo.user.service;

import com.kalgooksoo.user.command.UpdateUserCommand;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.repository.UserRepository;
import com.kalgooksoo.user.search.UserSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "spring.cloud.config.enabled=false"
})
@ActiveProfiles("test")
class UserServiceTest {

    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;

    @BeforeEach
    void setup() {
        userService = new DefaultUserService(userRepository, passwordEncoder);
        User account = User.create("tester", "1234", "테스터", null, null);
        testUser = userService.create(account);
    }

    @Test
    @DisplayName("계정을 생성합니다.")
    void createUserTest() {
        // Given
        User account = User.create("tester2", "1234", "테스터2", null, null);

        try {
            // When
            User createdUser = userService.create(account);

            // Then
            assertNotNull(createdUser);
        } catch (UsernameAlreadyExistsException e) {
            fail();
        }
    }

    @Test
    @DisplayName("계정 생성 시 이미 존재하는 아이디를 입력하면 UsernameAlreadyExistsException 예외를 발생시킵니다.")
    void createUserWithExistingUsernameTest() {
        // Given
        User account = User.create("tester2", "1234", "테스터2", null, null);
        userService.create(account);

        User invalidUser = User.create("tester2", "1234", "테스터2", null, null);

        // Then
        assertThrows(UsernameAlreadyExistsException.class, () -> userService.create(invalidUser));
    }

    @Test
    @DisplayName("계정 생성 시 계정 정책 날짜를 확인합니다.")
    void createUserWithPolicyTest() {
        // Given
        User account = User.create("tester2", "1234", "테스터2", null, null);

        try {
            // When
            User createdUser = userService.create(account);

            // Then
            LocalDateTime expectedExpiredAt = LocalDate.now().atTime(LocalTime.MIDNIGHT).plusYears(2L);
            LocalDateTime expectedCredentialsExpiredAt = LocalDate.now().atTime(LocalTime.MIDNIGHT).plusDays(180L);
            assertEquals(expectedExpiredAt, createdUser.getExpiredAt());
            assertEquals(expectedCredentialsExpiredAt, createdUser.getCredentialsExpiredAt());
        } catch (UsernameAlreadyExistsException e) {
            fail();
        }
    }

    @Test
    @DisplayName("계정 정보를 업데이트합니다.")
    void updateUserTest() {
        // Given
        UpdateUserCommand command = new UpdateUserCommand(
                "테스터 업데이트",
                "updated",
                "email.com",
                "010",
                "1234",
                "5678"
        );

        // When
        User updatedUser = userService.update(testUser.getId(), command);

        // Then
        assertEquals(command.name(), updatedUser.getName());
        assertEquals(command.emailId() + "@" + command.emailDomain(), updatedUser.getEmail().getValue());
        assertEquals(command.firstContactNumber() + "-" + command.middleContactNumber() + "-" + command.lastContactNumber(), updatedUser.getContactNumber().getValue());
    }

    @Test
    @DisplayName("계정을 ID로 찾습니다.")
    void findByIdTest() {
        // Given
        String id = testUser.getId();

        // When
        Optional<User> foundUser = userService.findById(id);

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals(id, foundUser.get().getId());
    }

    @Test
    @DisplayName("페이지네이션 정보에 기반한 계정 목록을 조회합니다.")
    void findAllTest() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // When
        Page<User> accounts = userService.findAll(pageRequest);

        // Then
        assertNotNull(accounts);
        assertTrue(accounts.getTotalElements() > 0);
    }

    @Test
    @DisplayName("페이지네이션 정보와 검색 조건에 기반한 계정 목록을 조회합니다.")
    void findAllWithCriteriaTest() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);
        UserSearch search = new UserSearch();
        search.setUsername("tester");

        // When
        Page<User> accounts = userService.findAll(search, pageRequest);

        // Then
        assertNotNull(accounts);
        assertTrue(accounts.getTotalElements() > 0);
    }


    @Test
    @DisplayName("계정을 ID로 삭제합니다.")
    void deleteByIdTest() {
        // Given
        String id = testUser.getId();

        // When
        userService.deleteById(id);

        // Then
        Optional<User> deletedUser = userService.findById(id);
        assertTrue(deletedUser.isEmpty());
    }

    @Test
    @DisplayName("계정의 패스워드를 업데이트합니다.")
    void updatePasswordTest() {
        // Given
        String id = testUser.getId();
        String newPassword = "newPassword";

        // When
        userService.updatePassword(id, newPassword);

        // Then
        Optional<User> updatedUser = userService.findById(id);
        assertTrue(updatedUser.isPresent());
        assertTrue(passwordEncoder.matches(newPassword, updatedUser.get().getPassword()));
    }
}