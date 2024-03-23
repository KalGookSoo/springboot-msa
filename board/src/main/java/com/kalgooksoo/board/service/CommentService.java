package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateCommentCommand;
import com.kalgooksoo.board.command.UpdateCommentCommand;
import com.kalgooksoo.board.domain.Comment;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * 댓글 서비스
 */
public interface CommentService {

    Comment create(@Nonnull CreateCommentCommand command);

    List<Comment> findAllByArticleId(@Nonnull String articleId);

    Comment update(@Nonnull String id, @Nonnull UpdateCommentCommand command);

    void delete(@Nonnull String id);

}
