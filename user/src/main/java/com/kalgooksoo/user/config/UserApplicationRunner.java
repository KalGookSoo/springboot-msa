package com.kalgooksoo.user.config;

import com.kalgooksoo.core.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.command.CreateUserCommand;
import com.kalgooksoo.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserApplicationRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(UserApplicationRunner.class);

    private final UserService userService;

    public UserApplicationRunner(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        CreateUserCommand command = new CreateUserCommand(
                "admin",
                "12341234",
                "관리자",
                "ga.miro3721",
                "gmail.com",
                "010",
                "1234",
                "5678"
        );
        try {
            this.userService.createAdmin(command);
        } catch (UsernameAlreadyExistsException e) {
            logger.info("계정이 이미 존재합니다");
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}