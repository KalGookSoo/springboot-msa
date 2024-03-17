package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Article;
import com.kalgooksoo.board.search.ArticleSearch;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    Page<Article> search(ArticleSearch search);

    Optional<Article> findById(String id);

    void deleteById(String id);

}
