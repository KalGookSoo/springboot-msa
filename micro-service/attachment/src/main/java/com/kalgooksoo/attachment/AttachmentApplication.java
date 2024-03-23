package com.kalgooksoo.attachment;

import com.kalgooksoo.core.exception.ExceptionHandlingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * 첨부파일 서비스 애플리케이션
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import({ExceptionHandlingController.class})
public class AttachmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AttachmentApplication.class, args);
    }
}