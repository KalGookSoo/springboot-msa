package com.kalgooksoo.core.principal;

public class AnonymousPrincipalProvider implements PrincipalProvider {
    @Override
    public String getUsername() {
        return "anonymous";
    }
}