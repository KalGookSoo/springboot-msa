package com.kalgooksoo.user;

import com.kalgooksoo.exception.ExceptionHandlingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * 계정 서비스 애플리케이션
 */
@EnableDiscoveryClient
@SpringBootApplication
@Import(ExceptionHandlingController.class)
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}