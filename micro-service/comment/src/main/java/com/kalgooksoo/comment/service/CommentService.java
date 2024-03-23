package com.kalgooksoo.comment.service;

import com.kalgooksoo.comment.command.CreateCommentCommand;
import com.kalgooksoo.comment.command.UpdateCommentCommand;
import com.kalgooksoo.comment.domain.Comment;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * 댓글 서비스
 */
public interface CommentService {

    Comment create(@Nonnull CreateCommentCommand command);

    List<Comment> findAllByArticleId(@Nonnull String articleId);

    Comment findById(@Nonnull String id);

    Comment update(@Nonnull String id, @Nonnull UpdateCommentCommand command);

    void delete(@Nonnull String id);

}
