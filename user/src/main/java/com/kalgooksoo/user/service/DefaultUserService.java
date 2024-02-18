package com.kalgooksoo.user.service;

import com.kalgooksoo.user.command.UpdateUserCommand;
import com.kalgooksoo.user.domain.Authority;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.repository.UserRepository;
import com.kalgooksoo.user.search.UserSearch;
import com.kalgooksoo.user.value.ContactNumber;
import com.kalgooksoo.user.value.Email;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.kalgooksoo.user.specification.UserSpecification.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) throws UsernameAlreadyExistsException {
        if (userRepository.exists(usernameEquals(user.getUsername()))) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        user.changePassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(String id, UpdateUserCommand command) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()) {
            throw new NoSuchElementException("계정을 찾을 수 없습니다.");
        }
        User user = foundUser.get();
        String name = command.name();
        Email email = new Email(command.emailId(), command.emailDomain());
        ContactNumber contactNumber = new ContactNumber(command.firstContactNumber(), command.middleContactNumber(), command.lastContactNumber());
        user.update(name, email, contactNumber);
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<User> findAll(UserSearch search, Pageable pageable) {
        Specification<User> specification = Specification.where(null);
        if (search.getUsername() != null) {
            specification = specification.and(usernameContains(search.getUsername()));
        }
        if (search.getName() != null) {
            specification = specification.and(nameContains(search.getName()));
        }
        if (search.getEmailId() != null) {
            specification = specification.and(emailIdContains(search.getEmailId()));
        }
        if (search.getContactNumber() != null) {
            specification = specification.and(contactNumberContains(search.getContactNumber()));
        }
        return userRepository.findAll(specification, pageable);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updatePassword(String id, String password) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            user.changePassword(passwordEncoder.encode(password));
        } else {
            throw new NoSuchElementException("User not found");
        }
    }


}
