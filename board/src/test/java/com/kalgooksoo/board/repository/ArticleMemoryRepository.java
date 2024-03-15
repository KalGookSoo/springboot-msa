package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Article;
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
    public List<Article> findAll() {
        return articles;
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
