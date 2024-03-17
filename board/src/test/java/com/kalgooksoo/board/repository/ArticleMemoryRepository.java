package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Article;
import com.kalgooksoo.board.search.ArticleSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ArticleMemoryRepository implements ArticleRepository {

    private final List<Article> articles = new ArrayList<>();

    @Override
    public Article save(Article article) {
        Assert.notNull(article, "게시글은 NULL이 될 수 없습니다");
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
    public Page<Article> search(ArticleSearch search) {
        Assert.notNull(search, "게시글 검색 조건은 NULL이 될 수 없습니다");
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
    public Optional<Article> findById(String id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        return articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst()
                .map(articles::remove)
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다."));
    }

}
