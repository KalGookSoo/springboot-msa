package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Article;
import com.kalgooksoo.board.search.ArticleSearch;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ArticleMemoryRepository implements ArticleRepository {

    private final List<Article> articles = new ArrayList<>();

    @Override
    public Article save(@Nonnull Article article) {
        if (article.getId() == null) {
            articles.add(article);
        } else {
            articles.stream()
                    .filter(a -> a.getId().equals(article.getId()))
                    .findFirst()
                    .map(a -> article)
                    .orElseGet(() -> {
                        articles.add(article);
                        return article;
                    });
        }
        return article;
    }

    @Override
    public Page<Article> search(@Nonnull ArticleSearch search) {
        Pageable pageable = search.pageable();
        List<Article> filteredArticles = articles.stream()
                .filter(article -> search.getCategoryId() == null || search.getCategoryId().equals(article.getCategoryId()))
                .filter(article -> search.getTitle() == null || article.getTitle().contains(search.getTitle()))
                .filter(article -> search.getContent() == null || article.getContent().contains(search.getContent()))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredArticles.size());
        return new PageImpl<>(filteredArticles.subList(start, end), pageable, filteredArticles.size());
    }

    @Override
    public Optional<Article> findById(@Nonnull String id) {
        return articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(@Nonnull String id) {
        articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst()
                .map(articles::remove)
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다."));
    }

}