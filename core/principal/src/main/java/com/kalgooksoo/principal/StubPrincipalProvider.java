package com.kalgooksoo.principal;

public class StubPrincipalProvider implements PrincipalProvider {
    @Override
    public String getUsername() {
        return "stub-username";
    }
}