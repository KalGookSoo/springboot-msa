package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.search.UserSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(String id);

    void deleteById(String id);

    Page<User> findAll(Pageable pageable);

    boolean existsByUsername(String username);

    Page<User> search(UserSearch search, Pageable pageable);

}