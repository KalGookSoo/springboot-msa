package com.kalgooksoo.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 사용자 인증 주체
 */
public class UserPrincipal implements UserDetails {

    private final String username;

    private final String password;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    private final Collection<String> authorities;

    public UserPrincipal(String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, Collection<String> authorities) {
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        boolean accountNonLocked = isAccountNonLocked();
        boolean accountNonExpired = isAccountNonExpired();
        boolean credentialsNonExpired = isCredentialsNonExpired();
        return accountNonLocked && accountNonExpired && credentialsNonExpired;
    }

}