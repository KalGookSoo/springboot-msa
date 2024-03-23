package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Comment;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentJpaRepository implements CommentRepository {

    private final EntityManager em;

    @Override
    public Comment save(@Nonnull Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
        return comment;
    }

    @Override
    public List<Comment> findAllByArticleId(@Nonnull String articleId) {
        return em.createQuery("select c from Comment c where c.articleId = :articleId", Comment.class)
                .setParameter("articleId", articleId)
                .getResultList();
    }

    @Override
    public Optional<Comment> findById(@Nonnull String id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public void deleteById(@Nonnull String id) {
        Comment comment = em.find(Comment.class, id);
        if (comment != null) {
            em.remove(comment);
        } else {
            throw new NoSuchElementException("댓글을 찾을 수 없습니다.");
        }
    }

}