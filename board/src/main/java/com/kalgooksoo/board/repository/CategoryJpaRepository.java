package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Category;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryJpaRepository implements CategoryRepository {

    private final EntityManager em;

    @Override
    public Category save(Category category) {
        Assert.notNull(category, "category는 null이 될 수 없습니다");
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
    public Optional<Category> findById(String id) {
        Assert.notNull(id, "id는 null이 될 수 없습니다");
        return Optional.ofNullable(em.find(Category.class, id));
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "id는 null이 될 수 없습니다");
        Category category = em.find(Category.class, id);
        if (category != null) {
            em.remove(category);
        } else {
            throw new NoSuchElementException("카테고리를 찾을 수 없습니다.");
        }
    }

}