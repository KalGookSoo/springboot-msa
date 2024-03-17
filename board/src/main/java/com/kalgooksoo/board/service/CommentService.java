package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateCommentCommand;
import com.kalgooksoo.board.command.UpdateCommentCommand;
import com.kalgooksoo.board.domain.Comment;

import java.util.List;

public interface CommentService {

    Comment create(CreateCommentCommand command);

    List<Comment> findAllByArticleId(String articleId);

    Comment update(String id, UpdateCommentCommand command);

    void delete(String id);

}
