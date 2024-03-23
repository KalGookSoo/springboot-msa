package com.kalgooksoo.article.service;

import com.kalgooksoo.article.command.CreateArticleCommand;
import com.kalgooksoo.article.command.MoveArticleCommand;
import com.kalgooksoo.article.command.UpdateArticleCommand;
import com.kalgooksoo.article.domain.Article;
import com.kalgooksoo.article.search.ArticleSearch;
import org.springframework.data.domain.Page;

public interface ArticleService {

    Article create(CreateArticleCommand command);

    Page<Article> search(ArticleSearch search);

    Article findById(String id);

    Article update(String id, UpdateArticleCommand command);

    void delete(String id);

    Article move(String id, MoveArticleCommand command);

}
