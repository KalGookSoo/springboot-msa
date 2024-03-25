package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.domain.Authority;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class AuthorityJpaRepository implements AuthorityRepository {

    private final EntityManager em;

    @Override
    public void save(@Nonnull Authority authority) {
        try {
            em.persist(authority);
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            em.merge(authority);
        }
    }

    @Override
    public List<Authority> findByUserId(@Nonnull String userId) {
        return em.createQuery("select authority from Authority authority where authority.userId = :userId", Authority.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public void deleteByUserId(@Nonnull String userId) {
        em.createQuery("delete from Authority authority where authority.userId = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }

}