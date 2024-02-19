package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.domain.Authority;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
@RequiredArgsConstructor
public class AuthorityJpaRepository implements AuthorityRepository {

    private final EntityManager em;

    @Override
    public Authority save(Authority authority) {
        Assert.notNull(authority, "authority must not be null");
        if (authority.getId() == null) {
            em.persist(authority);
        } else {
            em.merge(authority);
        }
        return authority;
    }

    @Override
    public Iterable<Authority> findByUserId(String userId) {
        Assert.notNull(userId, "userId must not be null");
        return em.createQuery("select authority from Authority authority where authority.userId = :userId", Authority.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public void deleteByUserId(String userId) {
        Assert.notNull(userId, "userId must not be null");
        em.createQuery("delete from Authority authority where authority.userId = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }

}