package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.domain.Authority;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorityMemoryRepository implements AuthorityRepository {

    private final List<Authority> authorities = new ArrayList<>();

    @Override
    public Authority save(Authority authority) {
        Assert.notNull(authority, "authority must not be null");
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
        return authority;
    }

    @Override
    public List<Authority> findByUserId(String userId) {
        return authorities.stream()
                .filter(user -> user.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByUserId(String userId) {
        authorities.removeIf(authority -> authority.getUserId().equals(userId));
    }

}
