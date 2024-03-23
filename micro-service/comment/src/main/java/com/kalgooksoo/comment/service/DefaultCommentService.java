package com.kalgooksoo.comment.service;

import com.kalgooksoo.comment.command.CreateCommentCommand;
import com.kalgooksoo.comment.command.UpdateCommentCommand;
import com.kalgooksoo.comment.domain.Comment;
import com.kalgooksoo.comment.repository.CommentRepository;
import com.kalgooksoo.core.principal.PrincipalProvider;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @see CommentService
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultCommentService implements CommentService {

    private final CommentRepository commentRepository;

    private final PrincipalProvider principalProvider;

    @Override
    public Comment create(@Nonnull CreateCommentCommand command) {
        String author = principalProvider.getUsername();
        Comment comment = Comment.create(command.articleId(), command.parentId(), command.content(), author);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllByArticleId(@Nonnull String articleId) {
        List<Comment> comments = commentRepository.findAllByArticleId(articleId);

        Map<String, List<Comment>> commentMap = comments.stream()
                .collect(Collectors.groupingBy(comment -> Optional.ofNullable(comment.getParentId()).orElse("ROOT")));

        return comments.stream()
                .filter(Comment::isRoot)
                .map(comment -> comment.mapChildren(comment, commentMap))
                .toList();
    }

    @Override
    public Comment findById(@Nonnull String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("댓글이 존재하지 않습니다."));
    }

    @Override
    public Comment update(@Nonnull String id, @Nonnull UpdateCommentCommand command) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("댓글이 존재하지 않습니다."));
        comment.update(command.content());
        return commentRepository.save(comment);
    }

    @Override
    public void delete(@Nonnull String id) {
        commentRepository.deleteById(id);
    }

}