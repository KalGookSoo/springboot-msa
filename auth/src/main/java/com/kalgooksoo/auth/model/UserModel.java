package com.kalgooksoo.auth.model;

import java.time.LocalDateTime;

public record UserModel(String id, String username, String password, LocalDateTime expiredAt, LocalDateTime lockedAt, LocalDateTime credentialsExpiredAt) {

    /**
     * 계정이 만료되지 않았는지 여부를 반환합니다.
     *
     * @return 계정이 만료되지 않았는지 여부
     */
    public boolean isAccountNonExpired() {
        return expiredAt == null || expiredAt.isAfter(LocalDateTime.now());
    }

    /**
     * 계정이 잠겨있지 않은지 여부를 반환합니다.
     *
     * @return 계정이 잠겨있지 않은지 여부
     */
    public boolean isAccountNonLocked() {
        return lockedAt == null || lockedAt.isBefore(LocalDateTime.now());
    }

    /**
     * 계정의 패스워드가 만료되지 않았는지 여부를 반환합니다.
     *
     * @return 계정의 패스워드가 만료되지 않았는지 여부
     */
    public boolean isCredentialsNonExpired() {
        return credentialsExpiredAt == null || credentialsExpiredAt.isAfter(LocalDateTime.now());
    }

    /**
     * 계정이 사용 가능한지 여부를 반환합니다.
     *
     * @return 계정이 사용 가능한지 여부
     */
    public boolean isEnabled() {
        boolean accountNonLocked = isAccountNonLocked();
        boolean accountNonExpired = isAccountNonExpired();
        boolean credentialsNonExpired = isCredentialsNonExpired();
        return accountNonLocked && accountNonExpired && credentialsNonExpired;
    }

}