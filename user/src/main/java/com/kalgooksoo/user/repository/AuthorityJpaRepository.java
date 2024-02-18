package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.domain.Authority;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorityJpaRepository implements AuthorityRepository {

    private final EntityManager em;

    @Override
    public Authority save(Authority authority) {
        em.persist(authority);
        return authority;
    }

}