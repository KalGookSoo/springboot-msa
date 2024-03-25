package com.kalgooksoo.article.repository;

import com.kalgooksoo.article.domain.Article;
import com.kalgooksoo.article.search.ArticleSearch;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class ArticleJpaRepository implements ArticleRepository {

    private final EntityManager em;

    @Override
    public Article save(@Nonnull Article article) {
        try {
            em.persist(article);
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            return em.merge(article);
        }
        return article;
    }

    @Override
    public Page<Article> search(@Nonnull ArticleSearch search) {
        Pageable pageable = search.pageable();
        String jpql = "select article from Article article where 1=1";
        jpql += generateJpql(search);

        TypedQuery<Article> query = em.createQuery(jpql, Article.class);
        setParameters(query, search);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Article> articles = query.getResultList();

        String countJpql = "select count(article) from Article article where 1=1";
        countJpql += generateJpql(search);

        TypedQuery<Long> countQuery = em.createQuery(countJpql, Long.class);
        setParameters(countQuery, search);

        Long count = countQuery.getSingleResult();

        return new PageImpl<>(articles, pageable, count);

    }

    @Override
    public Optional<Article> findById(@Nonnull String id) {
        return Optional.ofNullable(em.find(Article.class, id));
    }

    @Override
    public void deleteById(@Nonnull String id) {
        Article article = em.find(Article.class, id);
        if (article != null) {
            em.remove(article);
        } else {
            throw new NoSuchElementException("게시글을 찾을 수 없습니다.");
        }
    }

    private String generateJpql(@Nonnull ArticleSearch search) {
        StringBuilder jpql = new StringBuilder();
        jpql.append(" and article.categoryId = :categoryId");
        if (!search.isEmptyTitle()) {
            jpql.append(" and article.title like :title");
        }
        if (!search.isEmptyContent()) {
            jpql.append(" and article.content like :content");
        }
        if (!search.isEmptyAuthor()) {
            jpql.append(" and article.author = :author");
        }
        return jpql.toString();
    }

    private void setParameters(@Nonnull TypedQuery<?> query,@Nonnull ArticleSearch search) {
        query.setParameter("categoryId", search.getCategoryId());
        if (!search.isEmptyTitle()) {
            query.setParameter("title", "%" + search.getTitle() + "%");
        }
        if (!search.isEmptyContent()) {
            query.setParameter("content", "%" + search.getContent() + "%");
        }
        if (!search.isEmptyAuthor()) {
            query.setParameter("author", "%" + search.getAuthor() + "%");
        }
    }

}