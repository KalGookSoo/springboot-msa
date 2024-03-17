package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentJpaRepository implements CommentRepository {

    private final EntityManager em;

    @Override
    public Comment save(Comment comment) {
        Assert.notNull(comment, "댓글은 NULL이 될 수 없습니다");
        if (comment.getId() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
        return comment;
    }

    @Override
    public List<Comment> findAllByArticleId(String articleId) {
        Assert.notNull(articleId, "게시글 식별자는 NULL이 될 수 없습니다");
        return em.createQuery("select c from Comment c where c.articleId = :articleId", Comment.class)
                .setParameter("articleId", articleId)
                .getResultList();
    }

    @Override
    public Optional<Comment> findById(String id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        Comment comment = em.find(Comment.class, id);
        if (comment != null) {
            em.remove(comment);
        } else {
            throw new NoSuchElementException("댓글을 찾을 수 없습니다.");
        }
    }

}