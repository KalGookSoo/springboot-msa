package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.search.UserSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * 계정 저장소
 */
public interface UserRepository {

    User save(User user);

    Optional<User> findById(String id);

    Optional<User> findByUsername(String username);

    void deleteById(String id);

    Page<User> findAll(Pageable pageable);

    Page<User> search(UserSearch search, Pageable pageable);

}