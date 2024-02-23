package com.kalgooksoo.user.security.service;

import com.kalgooksoo.user.domain.Authority;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.repository.AuthorityRepository;
import com.kalgooksoo.user.repository.UserRepository;
import com.kalgooksoo.user.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * 사용자 상세 서비스
 */
@RequiredArgsConstructor
@Service
@Transactional
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        Set<Authority> authorities = new HashSet<>(authorityRepository.findByUserId(user.getId()));
        return UserPrincipal.create(user, authorities);
    }

    public UserDetails signUp(User user) throws UsernameAlreadyExistsException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException(user.getUsername(), "계정이 이미 존재합니다");
        }
        user.changePassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Authority authority = Authority.create(user.getId(), "ROLE_USER");
        authorityRepository.save(authority);
        return UserPrincipal.create(user, Set.of(authority));
    }

}