package com.kalgooksoo.user.config;

import com.kalgooksoo.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

@RequiredArgsConstructor
public class UserApplicationRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(UserApplicationRunner.class);

    private final UserService userService;

    @Override
    public void run(String... args) {
        User user = User.create("admin", "12341234", "관리자", null, null);
        try {
            this.userService.createAdmin(user);
        } catch (UsernameAlreadyExistsException e) {
            logger.info("계정이 이미 존재합니다");
        }
    }

}