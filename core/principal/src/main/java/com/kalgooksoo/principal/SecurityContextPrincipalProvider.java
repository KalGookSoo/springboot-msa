package com.kalgooksoo.principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityContextPrincipalProvider implements PrincipalProvider {
    @Override
    public String getUsername() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getName)
                .orElseThrow(IllegalStateException::new);
    }
}