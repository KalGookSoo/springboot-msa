package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Comment;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CommentMemoryRepository implements CommentRepository {

    private final List<Comment> comments = new ArrayList<>();

    @Override
    public Comment save(Comment comment) {
        Assert.notNull(comment, "댓글은 NULL이 될 수 없습니다");
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
    public List<Comment> findAllByArticleId(String articleId) {
        Assert.notNull(articleId, "게시글 식별자는 NULL이 될 수 없습니다");
        return comments.stream()
                .filter(comment -> comment.getArticleId().equals(articleId))
                .toList();
    }

    @Override
    public Optional<Comment> findById(String id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        return comments.stream()
                .filter(comment -> comment.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        comments.stream()
                .filter(comment -> comment.getId().equals(id))
                .findFirst()
                .map(comments::remove)
                .orElseThrow(() -> new NoSuchElementException("댓글을 찾을 수 없습니다."));
    }

}
