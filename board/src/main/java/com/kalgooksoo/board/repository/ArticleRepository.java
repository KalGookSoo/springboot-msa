package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    List<Article> findAllByCategoryId(String categoryId);

    Optional<Article> findById(String id);

    void deleteById(String id);

}
