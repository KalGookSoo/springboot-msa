package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateArticleCommand;
import com.kalgooksoo.board.command.MoveArticleCommand;
import com.kalgooksoo.board.command.UpdateArticleCommand;
import com.kalgooksoo.board.domain.Article;
import com.kalgooksoo.board.repository.ArticleRepository;
import com.kalgooksoo.board.search.ArticleSearch;
import com.kalgooksoo.core.principal.PrincipalProvider;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * @see ArticleService
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultArticleService implements ArticleService {

    private final ArticleRepository articleRepository;

    private final PrincipalProvider principalProvider;

    @Override
    public Article create(@Nonnull CreateArticleCommand command) {
        String author = principalProvider.getUsername();
        Article article = Article.create(command.title(), command.content(), command.categoryId(), author);
        return articleRepository.save(article);
    }

    @Override
    public Page<Article> search(@Nonnull ArticleSearch search) {
        return articleRepository.search(search);
    }

    @Override
    public Article findById(@Nonnull String id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
    }

    @Override
    public Article update(@Nonnull String id, @Nonnull UpdateArticleCommand command) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
        article.update(command.title(), command.content());
        return articleRepository.save(article);
    }

    @Override
    public void delete(@Nonnull String id) {
        articleRepository.deleteById(id);
    }

    @Override
    public Article move(@Nonnull String id, @Nonnull MoveArticleCommand command) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
        article.moveTo(command.categoryId());
        return articleRepository.save(article);
    }

}