package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Article;
import com.kalgooksoo.board.search.ArticleSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<Article> search(ArticleSearch search) {
        Assert.notNull(search, "게시글 검색 조건은 NULL이 될 수 없습니다");
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

    private String generateJpql(ArticleSearch search) {
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

    private void setParameters(TypedQuery<?> query, ArticleSearch search) {
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