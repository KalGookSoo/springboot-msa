package com.kalgooksoo.comment.repository;

import com.kalgooksoo.comment.domain.Comment;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CommentMemoryRepository implements CommentRepository {

    private final List<Comment> comments = new ArrayList<>();

    @Override
    public Comment save(@Nonnull Comment comment) {
        if (comment.getId() == null) {
            comments.add(comment);
        } else {
            comments.stream()
                    .filter(c -> c.getId().equals(comment.getId()))
                    .findFirst()
                    .map(c -> comment)
                    .orElseGet(() -> {
                        comments.add(comment);
                        return comment;
                    });
        }
        return comment;
    }

    @Override
    public List<Comment> findAllByArticleId(@Nonnull String articleId) {
        return comments.stream()
                .filter(comment -> comment.getArticleId().equals(articleId))
                .toList();
    }

    @Override
    public Optional<Comment> findById(@Nonnull String id) {
        return comments.stream()
                .filter(comment -> comment.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(@Nonnull String id) {
        comments.stream()
                .filter(comment -> comment.getId().equals(id))
                .findFirst()
                .map(comments::remove)
                .orElseThrow(() -> new NoSuchElementException("댓글을 찾을 수 없습니다."));
    }

}
