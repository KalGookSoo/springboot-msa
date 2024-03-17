package com.kalgooksoo.principal;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

public class HeaderPrincipalProvider implements PrincipalProvider {
    @Override
    public String getUsername() {
        // 현재 요청 헤더에서 "username"을 추출합니다.
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String username = attributes.getRequest().getHeader("username");
        return Optional.ofNullable(username)
                .orElse("anonymous");
    }
}
