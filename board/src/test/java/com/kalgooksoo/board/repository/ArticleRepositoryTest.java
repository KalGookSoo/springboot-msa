package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ArticleRepositoryTest {

    private ArticleRepository articleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup() {
        articleRepository = new ArticleJpaRepository(entityManager.getEntityManager());
    }

    @Test
    @DisplayName("게시글을 저장합니다. 성공 시 게시글을 반환합니다.")
    void saveShouldReturnArticle() {
        // Given
        Article article = Article.create("title", "content", "categoryId", "createdBy");

        // When
        Article savedArticle = articleRepository.save(article);

        // Then
        assertNotNull(savedArticle);
    }

    @Test
    @DisplayName("게시글을 저장합니다. 실패 시 IllegalArgumentException을 던집니다.")
    void saveShouldThrowIllegalArgumentException() {
        // Given
        Article article = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> articleRepository.save(article));
    }

    @Test
    @DisplayName("모든 게시글을 조회합니다. 성공 시 게시글 목록을 반환합니다.")
    void findAllShouldReturnArticles() {
        // Given
        Article article = Article.create("title", "content", "categoryId", "createdBy");
        Article savedArticle = articleRepository.save(article);

        // When
        List<Article> articles = articleRepository.findAll();

        // Then
        assertNotNull(articles);
        assertTrue(articles.stream().anyMatch(c -> c.getId().equals(savedArticle.getId())));
    }

    @Test
    @DisplayName("모든 게시글을 조회합니다. 실패 시 빈 목록을 반환합니다.")
    void findAllShouldReturnEmptyList() {
        // When
        List<Article> articles = articleRepository.findAll();

        // Then
        assertNotNull(articles);
        assertTrue(articles.isEmpty());
    }

    @Test
    @DisplayName("게시글을 조회합니다. 성공 시 게시글을 반환합니다.")
    void findByIdShouldReturnArticle() {
        // Given
        Article article = Article.create("title", "content", "categoryId", "createdBy");
        Article savedArticle = articleRepository.save(article);

        // When
        Optional<Article> foundArticle = articleRepository.findById(savedArticle.getId());

        // Then
        assertTrue(foundArticle.isPresent());
    }

    @Test
    @DisplayName("게시글을 조회합니다. 실패 시 빈 Optional을 반환합니다.")
    void findByIdShouldReturnEmptyOptional() {
        // Given
        String id = UUID.randomUUID().toString();

        // When
        Optional<Article> foundArticle = articleRepository.findById(id);

        // Then
        assertTrue(foundArticle.isEmpty());
    }

    @Test
    @DisplayName("게시글을 삭제합니다. 성공 시 삭제된 게시글을 조회할 수 없습니다.")
    void deleteTest() {
        // Given
        Article article = Article.create("title", "content", "categoryId", "createdBy");
        Article savedArticle = articleRepository.save(article);

        // When
        articleRepository.deleteById(savedArticle.getId());

        // Then
        Optional<Article> deletedArticle = articleRepository.findById(savedArticle.getId());
        assertTrue(deletedArticle.isEmpty());
    }

    @Test
    @DisplayName("게시글을 삭제합니다. 실패 시 IllegalArgumentException을 던집니다.")
    void deleteShouldThrowIllegalArgumentException() {
        // Given
        String id = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> articleRepository.deleteById(id));
    }

    @Test
    @DisplayName("게시글을 삭제합니다. 없는 게시글을 삭제 시 NoSuchElementException을 던집니다.")
    void deleteShouldThrowNoSuchElementException() {
        // Given
        String id = UUID.randomUUID().toString();

        // When & Then
        assertThrows(NoSuchElementException.class, () -> articleRepository.deleteById(id));
    }

}