package com.kalgooksoo.user.service;

import com.kalgooksoo.user.command.UpdateUserCommand;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.search.UserSearch;
import com.kalgooksoo.user.value.ContactNumber;
import com.kalgooksoo.user.value.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryUserService implements UserService {

    private final List<User> users = new ArrayList<>();

    @Override
    public User create(User user) throws UsernameAlreadyExistsException {
        if (users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        users.add(user);
        return user;
    }

    @Override
    public User update(String id, UpdateUserCommand command) {
        User user = findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
        Email email = new Email(command.emailId(), command.emailDomain());
        ContactNumber contactNumber = new ContactNumber(command.firstContactNumber(), command.middleContactNumber(), command.lastContactNumber());
        user.update(command.name(), email, contactNumber);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), users.size());
        return new PageImpl<>(users.subList(start, end), pageable, users.size());
    }

    @Override
    public Page<User> findAll(UserSearch search, Pageable pageable) {
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

    @Override
    public void deleteById(String id) {
        users.removeIf(user -> user.getId().equals(id));
    }

    @Override
    public void updatePassword(String id, String password) {
        User user = findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
        user.changePassword(password);
    }
}