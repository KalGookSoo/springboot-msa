package com.kalgooksoo.attachment.repository;

import com.kalgooksoo.attachment.domain.Attachment;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class AttachmentJpaRepository implements AttachmentRepository {

    private final EntityManager em;

    @Override
    public Attachment save(@Nonnull Attachment attachment) {
        try {
            em.persist(attachment);
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            return em.merge(attachment);
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
        return Optional.ofNullable(em.find(Attachment.class, id));
    }

    @Override
    public void deleteById(@Nonnull String id) {
        Attachment attachment = em.find(Attachment.class, id);
        if (attachment != null) {
            em.remove(attachment);
        } else {
            throw new NoSuchElementException("첨부파일을 찾을 수 없습니다.");
        }
    }

}