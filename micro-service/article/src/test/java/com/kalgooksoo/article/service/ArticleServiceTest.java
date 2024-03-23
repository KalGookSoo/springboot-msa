package com.kalgooksoo.article.service;

import com.kalgooksoo.article.command.CreateArticleCommand;
import com.kalgooksoo.article.command.MoveArticleCommand;
import com.kalgooksoo.article.command.UpdateArticleCommand;
import com.kalgooksoo.article.domain.Article;
import com.kalgooksoo.article.repository.ArticleMemoryRepository;
import com.kalgooksoo.article.repository.ArticleRepository;
import com.kalgooksoo.article.search.ArticleSearch;
import com.kalgooksoo.core.principal.PrincipalProvider;
import com.kalgooksoo.core.principal.StubPrincipalProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

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
        PrincipalProvider principalProvider = new StubPrincipalProvider();
        articleService = new DefaultArticleService(articleRepository, principalProvider);
    }

    @Test
    @DisplayName("게시글을 생성합니다. 성공 시 게시글을 반환합니다.")
    void createShouldReturnArticle() {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString());

        // When
        Article article = articleService.create(createArticleCommand);

        // Then
        assertNotNull(article);
    }

    @Test
    @DisplayName("카테고리 식별자로 게시글을 조회합니다. 성공 시 게시글 목록을 반환합니다.")
    void findAllByCategoryIdShouldReturnArticles() {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString());
        Article createdArticle = articleService.create(createArticleCommand);
        String categoryId = createdArticle.getCategoryId();
        ArticleSearch search = new ArticleSearch();
        search.setCategoryId(categoryId);

        // When
        Page<Article> page = articleService.search(search);

        // Then
        assertNotNull(page);
        assertFalse(page.isEmpty());
    }

    @Test
    @DisplayName("카테고리 식별자로 게시글을 조회합니다. 실패 시 빈 목록을 반환합니다.")
    void findAllByCategoryIdShouldReturnEmptyList() {
        // Given
        ArticleSearch search = new ArticleSearch();
        search.setCategoryId(UUID.randomUUID().toString());

        // When
        Page<Article> page = articleService.search(search);

        // Then
        assertNotNull(page);
        assertTrue(page.isEmpty());
    }

    @Test
    @DisplayName("게시글 식별자로 게시글을 조회합니다. 성공 시 게시글을 반환합니다.")
    void findByIdShouldReturnArticle() {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString());
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
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString());
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
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString());
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
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString());
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