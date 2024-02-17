package com.kalgooksoo.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 웹 MVC 설정을 위한 클래스입니다.
 * 이 클래스는 WebMvcConfigurer 인터페이스를 구현합니다.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 메시지 변환기를 설정하는 메소드입니다.
     * 이 메소드는 JSON 메시지 변환기에 대한 설정을 추가합니다.
     * LocalDateTime 타입응 응답 본문에 직렬화할 때 포맷을 변경합니다.
     *
     * @param converters HttpMessageConverter의 리스트입니다. 이 리스트에 새로운 메시지 변환기를 추가할 수 있습니다.
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }

    /**
     * CORS 설정을 추가하는 메소드입니다.
     * 이 메소드는 모든 경로에 대해 모든 출처, 메소드, 헤더를 허용하는 CORS 설정을 추가합니다.
     *
     * @param registry CORS 설정을 추가할 CorsRegistry입니다.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

}