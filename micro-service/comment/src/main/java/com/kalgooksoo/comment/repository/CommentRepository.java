package com.kalgooksoo.comment.repository;

import com.kalgooksoo.comment.domain.Comment;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(@Nonnull Comment comment);

    List<Comment> findAllByArticleId(@Nonnull String articleId);

    Optional<Comment> findById(@Nonnull String id);

    void deleteById(@Nonnull String id);

}
