package com.kalgooksoo.comment.repository;

import com.kalgooksoo.comment.domain.Comment;
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
class CommentRepositoryTest {

    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup() {
        commentRepository = new CommentJpaRepository(entityManager.getEntityManager());
    }

    @Test
    @DisplayName("댓글을 저장합니다. 성공 시 댓글을 반환합니다.")
    void saveShouldReturnComment() {
        // Given
        Comment comment = Comment.create(UUID.randomUUID().toString(), null, "content", "anonymous");

        // When
        Comment savedComment = commentRepository.save(comment);

        // Then
        assertNotNull(savedComment);
    }

    @Test
    @DisplayName("특정 게시글의 댓글을 조회합니다. 성공 시 댓글 목록을 반환합니다.")
    void findAllShouldReturnComments() {
        // Given
        Comment comment = Comment.create(UUID.randomUUID().toString(), null, "content", "anonymous");
        Comment savedComment = commentRepository.save(comment);
        String articleId = savedComment.getArticleId();

        // When
        List<Comment> comments = commentRepository.findAllByArticleId(articleId);

        // Then
        assertNotNull(comments);
        assertTrue(comments.stream().anyMatch(c -> c.getId().equals(savedComment.getId())));
    }

    @Test
    @DisplayName("특정 게시글의 댓글을 조회합니다. 실패 시 빈 목록을 반환합니다.")
    void findAllShouldReturnEmptyList() {
        // When
        List<Comment> comments = commentRepository.findAllByArticleId(UUID.randomUUID().toString());

        // Then
        assertNotNull(comments);
        assertTrue(comments.isEmpty());
    }

    @Test
    @DisplayName("댓글을 조회합니다. 성공 시 댓글을 반환합니다.")
    void findByIdShouldReturnComment() {
        // Given
        Comment comment = Comment.create(UUID.randomUUID().toString(), null, "content", "anonymous");
        Comment savedComment = commentRepository.save(comment);

        // When
        Optional<Comment> foundComment = commentRepository.findById(savedComment.getId());

        // Then
        assertTrue(foundComment.isPresent());
    }

    @Test
    @DisplayName("댓글을 조회합니다. 실패 시 빈 Optional을 반환합니다.")
    void findByIdShouldReturnEmptyOptional() {
        // Given
        String id = UUID.randomUUID().toString();

        // When
        Optional<Comment> foundComment = commentRepository.findById(id);

        // Then
        assertTrue(foundComment.isEmpty());
    }

    @Test
    @DisplayName("댓글을 삭제합니다. 성공 시 삭제된 댓글을 조회할 수 없습니다.")
    void deleteTest() {
        // Given
        Comment comment = Comment.create(UUID.randomUUID().toString(), null, "content", "anonymous");
        Comment savedComment = commentRepository.save(comment);

        // When
        commentRepository.deleteById(savedComment.getId());

        // Then
        Optional<Comment> deletedComment = commentRepository.findById(savedComment.getId());
        assertTrue(deletedComment.isEmpty());
    }

    @Test
    @DisplayName("댓글을 삭제합니다. 없는 댓글을 삭제 시 NoSuchElementException을 던집니다.")
    void deleteShouldThrowNoSuchElementException() {
        // Given
        String id = UUID.randomUUID().toString();

        // When & Then
        assertThrows(NoSuchElementException.class, () -> commentRepository.deleteById(id));
    }

}