package com.kalgooksoo.user.model;

import com.kalgooksoo.user.domain.Authority;
import com.kalgooksoo.user.domain.User;

import java.util.Collection;
import java.util.Set;

/**
 * 사용자 인증 주체
 */
public class UserPrincipal implements UserDetails {

    private User user;

    private Set<Authority> authorities;

    private UserPrincipal(User user, Set<Authority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user, Set<Authority> authorities) {
        return new UserPrincipal(user, authorities);
    }

    protected UserPrincipal() {
    }

    @Override
    public Collection<?> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

}