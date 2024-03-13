package com.kalgooksoo.user.service;

import com.kalgooksoo.user.command.UpdateUserCommand;
import com.kalgooksoo.user.domain.Authority;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.model.UserSummary;
import com.kalgooksoo.user.repository.AuthorityRepository;
import com.kalgooksoo.user.repository.UserRepository;
import com.kalgooksoo.user.search.UserSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 계정 서비스 테스트
 */
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private User testUser;

    @BeforeEach
    void setup() throws UsernameAlreadyExistsException {
        userService = new DefaultUserService(userRepository, authorityRepository);
        User user = User.create("tester", "12345678", "테스터", null, null);
        testUser = userService.createUser(user);
    }

    @Test
    @DisplayName("계정을 생성합니다.")
    void createUserTest() {
        // Given
        User user = User.create("tester2", "12345678", "테스터2", null, null);

        try {
            // When
            User createdUser = userService.createUser(user);

            // Then
            assertNotNull(createdUser);
        } catch (UsernameAlreadyExistsException e) {
            fail();
        }
    }

    @Test
    @DisplayName("계정 생성 시 이미 존재하는 아이디를 입력하면 UsernameAlreadyExistsException 예외를 발생시킵니다.")
    void createUserWithExistingUsernameTest() throws UsernameAlreadyExistsException {
        // Given
        User user = User.create("tester2", "12345678", "테스터2", null, null);
        userService.createUser(user);

        User invalidUser = User.create("tester2", "12345678", "테스터2", null, null);

        // Then
        assertThrows(UsernameAlreadyExistsException.class, () -> userService.createUser(invalidUser));
    }

    @Test
    @DisplayName("계정 생성 시 계정 정책 날짜를 확인합니다.")
    void createUserWithPolicyTest() {
        // Given
        User user = User.create("tester2", "12345678", "테스터2", null, null);

        try {
            // When
            User createdUser = userService.createUser(user);

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
        UpdateUserCommand command = new UpdateUserCommand("테스터 업데이트", "updated", "email.com", "010", "1234", "5678");

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
        Page<User> page = userService.findAll(pageRequest);

        // Then
        assertNotNull(page);
        assertTrue(page.getTotalElements() > 0);
    }

    @Test
    @DisplayName("페이지네이션 정보와 검색 조건에 기반한 계정 목록을 조회합니다.")
    void findAllWithCriteriaTest() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);
        UserSearch search = new UserSearch();
        search.setUsername("tester");

        // When
        Page<User> page = userService.findAll(search, pageRequest);

        // Then
        assertNotNull(page);
        assertTrue(page.getTotalElements() > 0);
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
        userService.updatePassword(id, "12345678", newPassword);

        // Then
        Optional<User> updatedUser = userService.findById(id);
        assertTrue(updatedUser.isPresent());
        assertTrue(passwordEncoder.matches(newPassword, updatedUser.get().getPassword()));
    }

    @Test
    @DisplayName("계정의 패스워드를 업데이트할 때 기존 패스워드가 일치하지 않으면 IllegalArgumentException 예외를 발생시킵니다.")
    void updatePasswordWithInvalidPasswordTest() {
        // Given
        String id = testUser.getId();
        String newPassword = "newPassword";

        // Then
        assertThrows(IllegalArgumentException.class, () -> userService.updatePassword(id, "invalidPassword", newPassword));
    }

    @Test
    @DisplayName("계정명과 패스워드로 계정을 확인합니다.")
    void verifyTest() {
        // Given
        String username = "tester";
        String password = "12345678";

        // When
        UserSummary verifiedUser = userService.verify(username, password);

        // Then
        assertNotNull(verifiedUser);
    }

    @Test
    @DisplayName("계정명과 패스워드로 계정을 확인할 때 계정명이 존재하지 않으면 IllegalArgumentException 예외를 발생시킵니다.")
    void verifyWithInvalidUsernameTest() {
        // Given
        String username = "invalidUsername";
        String password = "12345678";

        // Then
        assertThrows(IllegalArgumentException.class, () -> userService.verify(username, password));
    }

    @Test
    @DisplayName("계정명과 패스워드로 계정을 확인할 때 패스워드가 일치하지 않으면 IllegalArgumentException 예외를 발생시킵니다.")
    void verifyWithInvalidPasswordTest() {
        // Given
        String username = "tester";
        String password = "12345678";

        // Then
        assertThrows(IllegalArgumentException.class, () -> userService.verify(username, password + "invalid"));
    }

    @Test
    @DisplayName("계정에 종속된 권한을 조회합니다.")
    void findAuthoritiesByUserIdTest() {
        // Given
        String id = testUser.getId();

        // When
        Collection<Authority> authorities = userService.findAuthoritiesByUserId(id);

        // Then
        assertNotNull(authorities);
        assertFalse(authorities.isEmpty());
    }

}