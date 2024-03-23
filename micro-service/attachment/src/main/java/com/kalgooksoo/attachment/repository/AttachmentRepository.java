package com.kalgooksoo.attachment.repository;

import com.kalgooksoo.attachment.domain.Attachment;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository {

    Attachment save(@Nonnull Attachment attachment);

    List<Attachment> findAllByReferenceId(@Nonnull String referenceId);

    Optional<Attachment> findById(@Nonnull String id);

    void deleteById(@Nonnull String id);
}
