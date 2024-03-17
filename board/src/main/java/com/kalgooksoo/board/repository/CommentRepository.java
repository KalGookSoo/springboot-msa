package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    List<Comment> findAllByArticleId(String articleId);

    Optional<Comment> findById(String id);

    void deleteById(String id);

}
