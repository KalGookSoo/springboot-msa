package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateCommentCommand;
import com.kalgooksoo.board.command.UpdateCommentCommand;
import com.kalgooksoo.board.domain.Comment;
import com.kalgooksoo.board.repository.CommentMemoryRepository;
import com.kalgooksoo.board.repository.CommentRepository;
import com.kalgooksoo.core.principal.PrincipalProvider;
import com.kalgooksoo.core.principal.StubPrincipalProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 댓글 서비스 테스트
 */
class CommentServiceTest {

    private CommentService commentService;

    @BeforeEach
    void setup() {
        CommentRepository commentRepository = new CommentMemoryRepository();
        PrincipalProvider principalProvider = new StubPrincipalProvider();
        commentService = new DefaultCommentService(commentRepository, principalProvider);
    }

    @Test
    @DisplayName("댓글을 생성합니다. 성공 시 댓글을 반환합니다.")
    void createShouldReturnComment() {
        // Given
        CreateCommentCommand createCommentCommand = new CreateCommentCommand(UUID.randomUUID().toString(), null, "본문");

        // When
        Comment savedComment = commentService.create(createCommentCommand);

        // Then
        assertNotNull(savedComment);
    }

    @Test
    @DisplayName("모든 댓글을 조회합니다. 성공 시 댓글 목록을 반환합니다.")
    void findAllShouldReturnComments() {
        // Given
        CreateCommentCommand createCommentCommand = new CreateCommentCommand(UUID.randomUUID().toString(), null, "본문");
        Comment savedComment = commentService.create(createCommentCommand);

        // When
        List<Comment> comments = commentService.findAllByArticleId(savedComment.getArticleId());

        // Then
        assertFalse(comments.isEmpty());
    }

    @Test
    @DisplayName("모든 댓글을 조회합니다. 실패 시 빈 목록을 반환합니다.")
    void findAllShouldReturnEmptyList() {
        // Given
        String articleId = UUID.randomUUID().toString();

        // When
        List<Comment> comments = commentService.findAllByArticleId(articleId);

        // Then
        assertTrue(comments.isEmpty());
    }

    @Test
    @DisplayName("댓글을 수정합니다. 성공 시 수정된 댓글을 반환합니다.")
    void updateShouldReturnComment() {
        // Given
        CreateCommentCommand createCommentCommand = new CreateCommentCommand(UUID.randomUUID().toString(), null, "본문");
        Comment savedComment = commentService.create(createCommentCommand);
        UpdateCommentCommand updateCommentCommand = new UpdateCommentCommand("수정된 본문");

        // When
        commentService.update(savedComment.getId(), updateCommentCommand);

        // Then
        assertEquals("수정된 본문", savedComment.getContent());
    }

    @Test
    @DisplayName("댓글을 수정합니다. 존재하지 않는 댓글에 대해 수정을 시도할 경우 NoSuchElementException을 던집니다.")
    void updateShouldThrowNoSuchElementException() {
        // Given
        String id = UUID.randomUUID().toString();
        UpdateCommentCommand updateCommentCommand = new UpdateCommentCommand("수정된 본문");

        // When & Then
        assertThrows(NoSuchElementException.class, () -> commentService.update(id, updateCommentCommand));
    }

    @Test
    @DisplayName("댓글을 삭제합니다. 성공 시 삭제된 메뉴를 조회할 경우 NoSuchElementException을 던집니다.")
    void deleteTest() {
        // Given
        CreateCommentCommand createCommentCommand = new CreateCommentCommand(UUID.randomUUID().toString(), null, "본문");
        Comment savedComment = commentService.create(createCommentCommand);

        // When
        commentService.delete(savedComment.getId());

        // Then
        assertThrows(NoSuchElementException.class, () -> commentService.findById(savedComment.getId()));
    }

    @Test
    @DisplayName("댓글을 삭제합니다. 존재하지 않는 댓글에 대해 삭제를 시도할 경우 NoSuchElementException을 던집니다.")
    void deleteShouldThrowNoSuchElementException() {
        // Given
        String id = UUID.randomUUID().toString();

        // When & Then
        assertThrows(NoSuchElementException.class, () -> commentService.delete(id));
    }

}