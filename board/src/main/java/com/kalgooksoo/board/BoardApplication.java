package com.kalgooksoo.board;

import com.kalgooksoo.core.exception.ExceptionHandlingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * 게시판 서비스 애플리케이션
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import({ExceptionHandlingController.class})
public class BoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
    }
}