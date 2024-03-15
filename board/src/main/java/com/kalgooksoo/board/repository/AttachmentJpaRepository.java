package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Attachment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AttachmentJpaRepository implements AttachmentRepository {

    private final EntityManager em;

    @Override
    public Attachment save(Attachment attachment) {
        Assert.notNull(attachment, "첨부파일는 null이 될 수 없습니다");
        if (attachment.getId() == null) {
            em.persist(attachment);
        } else {
            em.merge(attachment);
        }
        return attachment;
    }

    @Override
    public List<Attachment> findAll() {
        return em.createQuery("select attachment from Attachment attachment", Attachment.class).getResultList();
    }

    @Override
    public Optional<Attachment> findById(String id) {
        Assert.notNull(id, "id는 null이 될 수 없습니다");
        return Optional.ofNullable(em.find(Attachment.class, id));
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "id는 null이 될 수 없습니다");
        Attachment attachment = em.find(Attachment.class, id);
        if (attachment != null) {
            em.remove(attachment);
        } else {
            throw new NoSuchElementException("첨부파일을 찾을 수 없습니다.");
        }
    }

}