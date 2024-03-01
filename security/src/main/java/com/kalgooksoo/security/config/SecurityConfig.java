package com.kalgooksoo.security.config;

import com.kalgooksoo.security.jwt.JwtAccessDeniedHandler;
import com.kalgooksoo.security.jwt.JwtAuthenticationEntryPoint;
import com.kalgooksoo.security.jwt.JwtFilter;
import com.kalgooksoo.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CSRF(Cross-Site Request Forgery) 설정 비활성화합니다. 현 애플리케이션은 무상태 애플리케이션이므로 비활성화합니다.
        http.csrf(AbstractHttpConfigurer::disable);

        // CORS(Cross-Origin Resource Sharing) 활성화. 다른 도메인에서의 요청을 허용합니다.
        http.cors(Customizer.withDefaults());

        // 세션 생성을 비활성화합니다.
        http.sessionManagement(ManagerFactoryParameters -> ManagerFactoryParameters.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 폼 로그인 비활성화합니다.
        http.formLogin(AbstractHttpConfigurer::disable);

        // HTTP 기본 인증 비활성화합니다.
        http.httpBasic(AbstractHttpConfigurer::disable);

        // 예외 발생 시 처리를 위한 핸들러를 설정합니다.
        http.exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler));

        // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가합니다.
        http.addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
                .requestMatchers(HttpMethod.GET, "/auth/token").authenticated()
                .requestMatchers(HttpMethod.POST, "/auth/token-refresh").authenticated()
                .anyRequest().permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}