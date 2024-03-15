package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Vote;
import com.kalgooksoo.board.domain.VoteId;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VoteJpaRepository implements VoteRepository {

    private final EntityManager em;

    @Override
    public Vote save(Vote vote) {
        Assert.notNull(vote, "투표는 NULL이 될 수 없습니다");
        if (vote.getId() == null) {
            em.persist(vote);
        } else {
            em.merge(vote);
        }
        return vote;
    }

    @Override
    public List<Vote> findAllByReferenceId(String referenceId) {
        Assert.notNull(referenceId, "참조 식별자는 NULL이 될 수 없습니다");
        String jpql = "select vote from Vote vote where vote.id.referenceId = :referenceId";
        return em.createQuery(jpql, Vote.class)
                .setParameter("referenceId", referenceId)
                .getResultList();
    }

    @Override
    public void deleteAllByReferenceId(String referenceId) {
        Assert.notNull(referenceId, "참조 식별자는 NULL이 될 수 없습니다");
        String jpql = "delete from Vote vote where vote.id.referenceId = :referenceId";
        em.createQuery(jpql)
                .setParameter("referenceId", referenceId)
                .executeUpdate();
    }

    @Override
    public void deleteById(VoteId id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        String jpql = "delete from Vote vote where vote.id = :id";
        em.createQuery(jpql)
                .setParameter("id", id)
                .executeUpdate();
    }

}
