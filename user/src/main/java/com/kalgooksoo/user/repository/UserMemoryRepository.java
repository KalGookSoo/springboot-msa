package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.search.UserSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserMemoryRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();

    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        if (user.getId() == null) {
            users.add(user);
        } else {
            // user.getId()와 같은 id를 가진 user를 찾아서 user로 교체
            users.stream()
                    .filter(u -> u.getId().equals(user.getId()))
                    .findFirst()
                    .ifPresent(u -> u = user);
        }
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public void deleteById(String id) {
        users.removeIf(user -> user.getId().equals(id));
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return new PageImpl<>(users, pageable, users.size());
    }

    @Override
    public Page<User> search(UserSearch search, Pageable pageable) {
        List<User> filteredUsers = users.stream()
                .filter(user -> search.getUsername() == null || user.getUsername().contains(search.getUsername()))
                .filter(user -> search.getName() == null || user.getName().contains(search.getName()))
                .filter(user -> search.getEmailId() == null || user.getEmail().getValue().contains(search.getEmailId()))
                .filter(user -> search.getContactNumber() == null || user.getContactNumber().getValue().contains(search.getContactNumber()))
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredUsers.size());
        return new PageImpl<>(filteredUsers.subList(start, end), pageable, filteredUsers.size());
    }

}
