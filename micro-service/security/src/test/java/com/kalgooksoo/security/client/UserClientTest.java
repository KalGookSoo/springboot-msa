package com.kalgooksoo.security.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalgooksoo.security.command.SignInCommand;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

//@SpringBootTest
class UserClientTest {

    private UserClient userClient = Mockito.mock(UserClient.class);

//    @Autowired
    private UserClient releasedUserClient;

    @Disabled
    @Test
    @DisplayName("로그인 API에 요청을 합니다.")
    void signInTest() {
        // Given
        SignInCommand command = new SignInCommand("admin", "12341234");

        // When
        Mono<JsonNode> mono = releasedUserClient.signIn(command);

        // Then
        mono.subscribe(
                result -> {
                    System.out.println("통신에 성공하였습니다.");
                    System.out.println(result);
                },
                error -> {
                    System.out.println("통신에 실패하였습니다.");
                    System.out.println(error.getMessage());
                },
                () -> {
                    System.out.println("통신에 완료되었습니다.");
                }
        );

        mono.block();
    }

    @Test
    @DisplayName("계정 인증 성공시 JsonNode를 반환합니다.")
    void signInShouldReturnJsonNodeOnSuccess() {
        // Given
        SignInCommand command = new SignInCommand("admin", "12341234");

        // When
        when(userClient.signIn(command)).thenReturn(Mono.just(new ObjectMapper().createObjectNode()));

        // Then
        Mono<JsonNode> mono = userClient.signIn(command);

        mono.subscribe(
                result -> {
                    System.out.println("통신에 성공하였습니다.");

                },
                error -> {
                    System.out.println("통신에 실패하였습니다.");
                    fail("통신에 실패하였습니다. " + error.getMessage());
                },
                () -> {
                    System.out.println("통신에 완료되었습니다.");
                }
        );

        mono.block();

    }

    @Test
    @DisplayName("계정 인증 실패시 에러를 반환합니다. ")
    void signInShouldReturnErrorOnFailure() {
        // Given
        SignInCommand command = new SignInCommand("admin", "invalid-password");

        // When
        when(userClient.signIn(command)).thenReturn(Mono.error(new RuntimeException("통신에 실패하였습니다")));

        // Then
        Mono<JsonNode> mono = userClient.signIn(command);

        assertThrows(RuntimeException.class, mono::block);

    }

}