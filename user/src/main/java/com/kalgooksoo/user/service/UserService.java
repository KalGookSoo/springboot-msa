package com.kalgooksoo.user.service;

import com.kalgooksoo.user.command.UpdateUserCommand;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.search.UserSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    User create(User user) throws UsernameAlreadyExistsException;

    User update(String id, UpdateUserCommand command);

    Optional<User> findById(String id);

    Page<User> findAll(Pageable pageable);

    Page<User> findAll(UserSearch search, Pageable pageable);

    void deleteById(String id);

    void updatePassword(String id, String password);

}
