package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Article;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleJpaRepository implements ArticleRepository {

    private final EntityManager em;

    @Override
    public Article save(Article article) {
        Assert.notNull(article, "게시글은 NULL이 될 수 없습니다");
        if (article.getId() == null) {
            em.persist(article);
        } else {
            em.merge(article);
        }
        return article;
    }

    @Override
    public List<Article> findAllByCategoryId(String categoryId) {
        Assert.notNull(categoryId, "카테고리 식별자는 NULL이 될 수 없습니다");
        return em.createQuery("select a from Article a where a.categoryId = :categoryId", Article.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public Optional<Article> findById(String id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        return Optional.ofNullable(em.find(Article.class, id));
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        Article article = em.find(Article.class, id);
        if (article != null) {
            em.remove(article);
        } else {
            throw new NoSuchElementException("게시글을 찾을 수 없습니다.");
        }
    }

}