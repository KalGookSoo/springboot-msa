package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateArticleCommand;
import com.kalgooksoo.board.command.MoveArticleCommand;
import com.kalgooksoo.board.command.UpdateArticleCommand;
import com.kalgooksoo.board.domain.Article;
import com.kalgooksoo.board.repository.ArticleMemoryRepository;
import com.kalgooksoo.board.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 게시글 서비스 테스트
 */
class ArticleServiceTest {

    private ArticleService articleService;

    @BeforeEach
    void setup() {
        ArticleRepository articleRepository = new ArticleMemoryRepository();
        articleService = new DefaultArticleService(articleRepository);
    }

    @Test
    @DisplayName("게시글을 생성합니다. 성공 시 게시글을 반환합니다.")
    void createShouldReturnArticle() {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString(), "작성자");

        // When
        Article article = articleService.create(createArticleCommand);

        // Then
        assertNotNull(article);
    }

    @Test
    @DisplayName("게시글을 생성합니다. 실패 시 IllegalArgumentException을 던집니다.")
    void createShouldThrowIllegalArgumentException() {
        // Given
        CreateArticleCommand createArticleCommand = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> articleService.create(createArticleCommand));
    }

    @Test
    @DisplayName("카테고리 식별자로 게시글을 조회합니다. 성공 시 게시글 목록을 반환합니다.")
    void findAllByCategoryIdShouldReturnArticles() {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString(), "작성자");
        Article createdArticle = articleService.create(createArticleCommand);
        String categoryId = createdArticle.getCategoryId();

        // When
        List<Article> articles = articleService.findAllByCategoryId(categoryId);

        // Then
        assertNotNull(articles);
        assertFalse(articles.isEmpty());
    }

    @Test
    @DisplayName("카테고리 식별자로 게시글을 조회합니다. 실패 시 빈 목록을 반환합니다.")
    void findAllByCategoryIdShouldReturnEmptyList() {
        // Given
        String categoryId = UUID.randomUUID().toString();

        // When
        List<Article> articles = articleService.findAllByCategoryId(categoryId);

        // Then
        assertTrue(articles.isEmpty());
    }

    @Test
    @DisplayName("게시글 식별자로 게시글을 조회합니다. 성공 시 게시글을 반환합니다.")
    void findByIdShouldReturnArticle() {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString(), "작성자");
        Article createdArticle = articleService.create(createArticleCommand);
        String id = createdArticle.getId();

        // When
        Article article = articleService.findById(id);

        // Then
        assertNotNull(article);
    }

    @Test
    @DisplayName("게시글 식별자로 게시글을 조회합니다. 실패 시 NoSuchElementException을 던집니다.")
    void findByIdShouldThrowNoSuchElementException() {
        // Given
        String id = UUID.randomUUID().toString();

        // When & Then
        assertThrows(NoSuchElementException.class, () -> articleService.findById(id));
    }

    @Test
    @DisplayName("게시글을 수정합니다. 성공 시 수정된 게시글을 반환합니다.")
    void updateShouldUpdateArticle() {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString(), "작성자");
        Article createdArticle = articleService.create(createArticleCommand);
        String id = createdArticle.getId();
        UpdateArticleCommand updateArticleCommand = new UpdateArticleCommand("Updated title", "Updated content");

        // When
        Article updatedArticle = articleService.update(id, updateArticleCommand);

        // Then
        assertEquals("Updated title", updatedArticle.getTitle());
        assertEquals("Updated content", updatedArticle.getContent());
    }

    @Test
    @DisplayName("게시글을 수정합니다. 실패 시 NoSuchElementException을 던집니다.")
    void updateShouldThrowNoSuchElementException() {
        // Given
        String id = UUID.randomUUID().toString();
        UpdateArticleCommand updateArticleCommand = new UpdateArticleCommand("Updated title", "Updated content");

        // When & Then
        assertThrows(NoSuchElementException.class, () -> articleService.update(id, updateArticleCommand));
    }

    @Test
    @DisplayName("게시글을 삭제합니다. 성공 시 삭제된 게시글을 조회할 경우 NoSuchElementException을 던집니다.")
    void deleteShouldDeleteArticle() {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString(), "작성자");
        Article createdArticle = articleService.create(createArticleCommand);
        String id = createdArticle.getId();

        // When
        articleService.delete(id);

        // Then
        assertThrows(NoSuchElementException.class, () -> articleService.findById(id));
    }

    @Test
    @DisplayName("게시글을 이동합니다. 성공 시 이동된 게시글을 반환합니다.")
    void moveShouldReturnMovedArticle() {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString(), "작성자");
        Article createdArticle = articleService.create(createArticleCommand);
        String id = createdArticle.getId();
        MoveArticleCommand moveArticleCommand = new MoveArticleCommand(UUID.randomUUID().toString());

        // When
        articleService.move(id, moveArticleCommand);

        // Then
        assertEquals(moveArticleCommand.categoryId(), articleService.findById(id).getCategoryId());
    }

    @Test
    @DisplayName("게시글을 이동합니다. 실패 시 NoSuchElementException을 던집니다.")
    void moveShouldThrowNoSuchElementException() {
        // Given
        String id = UUID.randomUUID().toString();
        MoveArticleCommand moveArticleCommand = new MoveArticleCommand(UUID.randomUUID().toString());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> articleService.move(id, moveArticleCommand));
    }

}