package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.View;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ViewJpaRepository implements ViewRepository {

    private final EntityManager em;

    @Override
    public View save(@Nonnull View view) {
        if (view.getId() == null) {
            em.persist(view);
        } else {
            em.merge(view);
        }
        return view;
    }

    @Override
    public List<View> findAllByReferenceId(@Nonnull String referenceId) {
        String jpql = "select view from View view where view.id.referenceId = :referenceId";
        return em.createQuery(jpql, View.class)
                .setParameter("referenceId", referenceId)
                .getResultList();
    }

    @Override
    public void deleteAllByReferenceId(@Nonnull String referenceId) {
        String jpql = "delete from View view where view.id.referenceId = :referenceId";
        em.createQuery(jpql)
                .setParameter("referenceId", referenceId)
                .executeUpdate();
    }

}
