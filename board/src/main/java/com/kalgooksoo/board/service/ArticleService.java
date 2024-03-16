package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateArticleCommand;
import com.kalgooksoo.board.command.MoveArticleCommand;
import com.kalgooksoo.board.command.UpdateArticleCommand;
import com.kalgooksoo.board.domain.Article;

import java.util.List;

public interface ArticleService {

    Article create(CreateArticleCommand command);

    List<Article> findAllByCategoryId(String categoryId);

    Article findById(String id);

    Article update(String id, UpdateArticleCommand command);

    void delete(String id);

    void move(String id, MoveArticleCommand command);

}
