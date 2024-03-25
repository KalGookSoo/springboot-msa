package com.kalgooksoo.view.repository;

import com.kalgooksoo.view.domain.View;
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
public class ViewJpaRepository implements ViewRepository {

    private final EntityManager em;

    @Override
    public View save(@Nonnull View view) {
        try {
            em.persist(view);
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            return em.merge(view);
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
