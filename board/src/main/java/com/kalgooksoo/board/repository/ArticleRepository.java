package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Article;
import com.kalgooksoo.board.search.ArticleSearch;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ArticleRepository {

    Article save(@Nonnull Article article);

    Page<Article> search(@Nonnull ArticleSearch search);

    Optional<Article> findById(@Nonnull String id);

    void deleteById(@Nonnull String id);

}
