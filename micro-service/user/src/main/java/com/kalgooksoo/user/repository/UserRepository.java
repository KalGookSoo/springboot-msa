package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.search.UserSearch;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * 계정 저장소
 */
public interface UserRepository {

    User save(@Nonnull User user);

    Optional<User> findById(@Nonnull String id);

    Optional<User> findByUsername(@Nonnull String username);

    void deleteById(@Nonnull String id);

    Page<User> findAll(@Nonnull Pageable pageable);

    Page<User> search(@Nonnull UserSearch search, @Nonnull Pageable pageable);

}