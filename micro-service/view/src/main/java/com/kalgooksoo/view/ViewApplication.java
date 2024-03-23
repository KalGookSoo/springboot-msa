package com.kalgooksoo.view;

import com.kalgooksoo.core.exception.ExceptionHandlingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * 뷰 서비스 애플리케이션
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import({ExceptionHandlingController.class})
public class ViewApplication {
    public static void main(String[] args) {
        SpringApplication.run(ViewApplication.class, args);
    }
}