package com.kalgooksoo.vote.repository;

import com.kalgooksoo.vote.domain.Vote;
import com.kalgooksoo.vote.domain.VoteId;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class VoteJpaRepository implements VoteRepository {

    private final EntityManager em;

    @Override
    public Vote save(@Nonnull Vote vote) {
        if (vote.getId() == null) {
            em.persist(vote);
        } else {
            em.merge(vote);
        }
        return vote;
    }

    @Override
    public List<Vote> findAllByReferenceId(@Nonnull String referenceId) {
        String jpql = "select vote from Vote vote where vote.id.referenceId = :referenceId";
        return em.createQuery(jpql, Vote.class)
                .setParameter("referenceId", referenceId)
                .getResultList();
    }

    @Override
    public void deleteAllByReferenceId(@Nonnull String referenceId) {
        String jpql = "delete from Vote vote where vote.id.referenceId = :referenceId";
        em.createQuery(jpql)
                .setParameter("referenceId", referenceId)
                .executeUpdate();
    }

    @Override
    public void deleteById(@Nonnull VoteId id) {
        String jpql = "delete from Vote vote where vote.id = :id";
        em.createQuery(jpql)
                .setParameter("id", id)
                .executeUpdate();
    }

}
