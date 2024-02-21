package com.kalgooksoo.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "spring.cloud.config.enabled=false"
})
@ActiveProfiles("test")
class JwtProviderTest {

    private JwtProvider jwtProvider;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider("security", 1800000);
    }

    @Test
    @DisplayName("Generates valid token for given authentication")
    void generatesValidToken() {
        when(authentication.getName()).thenReturn("user");
//        when(authentication.getAuthorities()).thenReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        String token = jwtProvider.generateToken(authentication);

        assertNotNull(token);
        assertEquals("user", jwtProvider.getUsername(token));
    }

    @Test
    @DisplayName("Returns null username for invalid token")
    void returnsNullUsernameForInvalidToken() {
        String invalidToken = "invalidToken";

        assertNull(jwtProvider.getUsername(invalidToken));
    }

    @Test
    @DisplayName("Validates token successfully")
    void validatesTokenSuccessfully() {
        when(authentication.getName()).thenReturn("user");
//        when(authentication.getAuthorities()).thenReturn();

        String token = jwtProvider.generateToken(authentication);

        assertTrue(jwtProvider.validateToken(token));
    }

    @Test
    @DisplayName("Fails to validate invalid token")
    void failsToValidateInvalidToken() {
        String invalidToken = "invalidToken";

        assertFalse(jwtProvider.validateToken(invalidToken));
    }
}