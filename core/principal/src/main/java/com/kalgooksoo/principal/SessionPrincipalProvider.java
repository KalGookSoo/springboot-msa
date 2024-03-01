package com.kalgooksoo.principal;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

public class SessionPrincipalProvider implements PrincipalProvider {
    @Override
    public String getUsername() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        return Optional.ofNullable(session.getAttribute("username"))
                .map(Object::toString)
                .orElseThrow(IllegalStateException::new);

    }
}