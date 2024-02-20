package com.kalgooksoo.user.model;

import java.io.Serializable;
import java.util.Collection;

/**
 * 사용자 상세 정보를 나타내는 인터페이스입니다.
 * 이 인터페이스는 {@link <a href="https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetails.html">"Spring Security의 UserDetails"</a>}를 참조하여 작성되었습니다.
 * 현재 계정 모듈에서는 spring security 의존성을 배제하였습니다.
 * 따라서 spring security 의존성을 추가할 경우 갈아끼울 수 있도록 합니다.
 */
public interface UserDetails extends Serializable {

    Collection<?> getAuthorities();

    String getPassword();

    String getUsername();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();

    boolean isEnabled();

}