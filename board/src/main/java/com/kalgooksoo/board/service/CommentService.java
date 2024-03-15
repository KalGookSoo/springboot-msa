package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateCommentCommand;
import com.kalgooksoo.board.command.DislikesCommand;
import com.kalgooksoo.board.command.LikesCommand;
import com.kalgooksoo.board.command.UpdateCommentCommand;
import com.kalgooksoo.board.domain.Comment;

public interface CommentService {

    Comment create(CreateCommentCommand command);

    Comment update(String id, UpdateCommentCommand command);

    void delete(String id);

    void likes(String id, LikesCommand command);

    void dislikes(String id, DislikesCommand command);

}
