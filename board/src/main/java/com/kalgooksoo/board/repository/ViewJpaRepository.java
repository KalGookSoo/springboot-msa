package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.View;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ViewJpaRepository implements ViewRepository {

    private final EntityManager em;

    @Override
    public View save(View view) {
        Assert.notNull(view, "뷰는 NULL이 될 수 없습니다");
        if (view.getId() == null) {
            em.persist(view);
        } else {
            em.merge(view);
        }
        return view;
    }

    @Override
    public List<View> findAllByReferenceId(String referenceId) {
        Assert.notNull(referenceId, "참조 식별자는 NULL이 될 수 없습니다");
        String jpql = "select view from View view where view.id.referenceId = :referenceId";
        return em.createQuery(jpql, View.class)
                .setParameter("referenceId", referenceId)
                .getResultList();
    }

    @Override
    public void deleteAllByReferenceId(String referenceId) {
        Assert.notNull(referenceId, "참조 식별자는 NULL이 될 수 없습니다");
        String jpql = "delete from View view where view.id.referenceId = :referenceId";
        em.createQuery(jpql)
                .setParameter("referenceId", referenceId)
                .executeUpdate();
    }

}
