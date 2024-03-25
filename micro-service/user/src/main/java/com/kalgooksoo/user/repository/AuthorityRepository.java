package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.domain.Authority;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * 권한 저장소
 */
public interface AuthorityRepository  {

    void save(@Nonnull Authority authority);

    List<Authority> findByUserId(@Nonnull String userId);

    void deleteByUserId(@Nonnull String userId);

}