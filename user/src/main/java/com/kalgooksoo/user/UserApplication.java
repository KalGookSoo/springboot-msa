package com.kalgooksoo.user;

import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.service.DefaultUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 계정 서비스 애플리케이션
 */
@EnableDiscoveryClient
@SpringBootApplication
public class UserApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(UserApplication.class);

    private final DefaultUserService defaultUserService;

    public UserApplication(DefaultUserService defaultUserService) {
        this.defaultUserService = defaultUserService;
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Override
    public void run(String... args) {
        User user = User.create("admin", "1234", "관리자", null, null);
        try {
            this.defaultUserService.createAdmin(user);
        } catch (UsernameAlreadyExistsException e) {
            logger.info("계정이 이미 존재합니다");
        }
    }
}
