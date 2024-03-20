package com.kalgooksoo.user.service;

import com.kalgooksoo.core.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.command.CreateUserCommand;
import com.kalgooksoo.user.command.UpdateUserCommand;
import com.kalgooksoo.user.domain.Authority;
import com.kalgooksoo.user.domain.ContactNumber;
import com.kalgooksoo.user.domain.Email;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.model.UserSummary;
import com.kalgooksoo.user.repository.AuthorityRepository;
import com.kalgooksoo.user.repository.UserRepository;
import com.kalgooksoo.user.search.UserSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @see UserService
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * @see UserService#createUser(CreateUserCommand)
     */
    @Override
    public User createUser(CreateUserCommand command) throws UsernameAlreadyExistsException {
        if (userRepository.findByUsername(command.username()).isPresent()) {
            throw new UsernameAlreadyExistsException(command.username(), "계정이 이미 존재합니다");
        }

        Email email = new Email(command.emailId(), command.emailDomain());
        ContactNumber contactNumber = new ContactNumber(command.firstContactNumber(), command.middleContactNumber(), command.lastContactNumber());
        User user = User.create(command.username(), command.password(), command.name(), email, contactNumber);
        user.changePassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        authorityRepository.save(Authority.create(savedUser.getId(), "ROLE_USER"));
        return userRepository.save(user);
    }

    /**
     * @see UserService#createAdmin(CreateUserCommand)
     */
    @Override
    public User createAdmin(CreateUserCommand command) throws UsernameAlreadyExistsException {
        if (userRepository.findByUsername(command.username()).isPresent()) {
            throw new UsernameAlreadyExistsException(command.username(), "계정이 이미 존재합니다");
        }

        Email email = new Email(command.emailId(), command.emailDomain());
        ContactNumber contactNumber = new ContactNumber(command.firstContactNumber(), command.middleContactNumber(), command.lastContactNumber());
        User user = User.create(command.username(), command.password(), command.name(), email, contactNumber);
        user.changePassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        authorityRepository.save(Authority.create(savedUser.getId(), "ROLE_ADMIN"));
        return userRepository.save(user);
    }

    /**
     * @see UserService#update(String, UpdateUserCommand)
     */
    @Override
    public User update(String id, UpdateUserCommand command) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("계정을 찾을 수 없습니다."));
        String name = command.name();
        Email email = new Email(command.emailId(), command.emailDomain());
        ContactNumber contactNumber = new ContactNumber(command.firstContactNumber(), command.middleContactNumber(), command.lastContactNumber());
        user.update(name, email, contactNumber);
        return userRepository.save(user);
    }

    /**
     * @see UserService#findById(String)
     */
    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    /**
     * @see UserService#findAll(Pageable)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * @see UserService#findAll(UserSearch, Pageable)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<User> findAll(UserSearch search, Pageable pageable) {
        return userRepository.search(search, pageable);
    }

    /**
     * @see UserService#delete(String)
     * 삭제 연산은 권한을 가진자에게만 허용할 예정이라 노출해도 무방할 것 같다고 판단하여 예외 처리를 하였습니다.
     */
    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
        authorityRepository.deleteByUserId(id);
    }

    /**
     * @see UserService#updatePassword(String, String, String)
     */
    @Override
    public void updatePassword(String id, String originPassword, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("계정을 찾을 수 없습니다."));
        if (!passwordEncoder.matches(originPassword, user.getPassword())) {
            throw new IllegalArgumentException("계정 정보가 일치하지 않습니다.");
        }
        user.changePassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * 계정 정보를 검증합니다.
     *
     * @param username 계정명
     * @param password 패스워드
     * @return 계정 정보가 일치하면 계정 정보를 반환합니다.
     * @throws IllegalArgumentException 보안상 이유로 계정 정보가 일치하지 않는 경우, 계정을 찾지 못한 경우 모두 같은 예외를 발생시킵니다.
     * @see UserService#verify(String, String)
     */
    @Override
    public UserSummary verify(String username, String password) {
        Assert.notNull(username, "계정명이 필요합니다.");
        Assert.notNull(password, "패스워드가 필요합니다.");
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("계정 정보가 일치하지 않습니다."));
        if (passwordEncoder.matches(password, user.getPassword())) {
            List<Authority> authorities = authorityRepository.findByUserId(user.getId());
            return new UserSummary(user, authorities);
        }
        throw new IllegalArgumentException("계정 정보가 일치하지 않습니다.");
    }

    /**
     * @see UserService#findAuthoritiesByUserId(String)
     */
    @Override
    public List<Authority> findAuthoritiesByUserId(String userId) {
        return authorityRepository.findByUserId(userId);
    }

}