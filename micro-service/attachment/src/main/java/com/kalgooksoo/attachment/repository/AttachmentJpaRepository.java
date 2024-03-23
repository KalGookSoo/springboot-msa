package com.kalgooksoo.attachment.repository;

import com.kalgooksoo.attachment.domain.Attachment;
import jakarta.annotation.Nonnull;
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
    public Attachment save(@Nonnull Attachment attachment) {
        Assert.notNull(attachment, "첨부파일는 NULL이 될 수 없습니다");
        if (attachment.getId() == null) {
            em.persist(attachment);
        } else {
            em.merge(attachment);
        }
        return attachment;
    }

    @Override
    public List<Attachment> findAllByReferenceId(@Nonnull String referenceId) {
        String jpql = "select attachment from Attachment attachment where attachment.referenceId = :referenceId";
        return em.createQuery(jpql, Attachment.class)
                .setParameter("referenceId", referenceId)
                .getResultList();
    }

    @Override
    public Optional<Attachment> findById(@Nonnull String id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        return Optional.ofNullable(em.find(Attachment.class, id));
    }

    @Override
    public void deleteById(@Nonnull String id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        Attachment attachment = em.find(Attachment.class, id);
        if (attachment != null) {
            em.remove(attachment);
        } else {
            throw new NoSuchElementException("첨부파일을 찾을 수 없습니다.");
        }
    }

}