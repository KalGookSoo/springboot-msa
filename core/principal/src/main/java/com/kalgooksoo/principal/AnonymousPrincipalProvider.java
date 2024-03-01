package com.kalgooksoo.principal;

public class AnonymousPrincipalProvider implements PrincipalProvider {
    @Override
    public String getUsername() {
        return "anonymous";
    }
}