package com.kalgooksoo.category.repository;

import com.kalgooksoo.category.domain.Category;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class CategoryJpaRepository implements CategoryRepository {

    private final EntityManager em;

    @Override
    public Category save(@Nonnull Category category) {
        if (category.getId() == null) {
            em.persist(category);
        } else {
            em.merge(category);
        }
        return category;
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery("select category from Category category", Category.class).getResultList();
    }

    @Override
    public Optional<Category> findById(@Nonnull String id) {
        return Optional.ofNullable(em.find(Category.class, id));
    }

    @Override
    public void deleteById(@Nonnull String id) {
        Category category = em.find(Category.class, id);
        if (category != null) {
            em.remove(category);
        } else {
            throw new NoSuchElementException("카테고리를 찾을 수 없습니다.");
        }
    }

}