package com.kalgooksoo.menu;

import com.kalgooksoo.core.exception.ExceptionHandlingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * 메뉴 서비스 애플리케이션
 */
@EnableDiscoveryClient
@SpringBootApplication
@Import({ExceptionHandlingController.class})
public class MenuApplication {

    public static void main(String[] args) {
        SpringApplication.run(MenuApplication.class, args);
    }

}