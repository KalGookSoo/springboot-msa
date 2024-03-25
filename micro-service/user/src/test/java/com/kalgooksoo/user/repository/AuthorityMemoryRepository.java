package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.domain.Authority;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorityMemoryRepository implements AuthorityRepository {

    private final List<Authority> authorities = new ArrayList<>();

    @Override
    public void save(@Nonnull Authority authority) {
        if (authority.getId() == null) {
            authorities.add(authority);
        } else {
            authorities.stream()
                    .filter(u -> u.getId().equals(authority.getId()))
                    .findFirst()
                    .map(u -> authority)
                    .orElseGet(() -> {
                        authorities.add(authority);
                        return authority;
                    });
        }
    }

    @Override
    public List<Authority> findByUserId(@Nonnull String userId) {
        return authorities.stream()
                .filter(user -> user.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByUserId(@Nonnull String userId) {
        authorities.removeIf(authority -> authority.getUserId().equals(userId));
    }

}
