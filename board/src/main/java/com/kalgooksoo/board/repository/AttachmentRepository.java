package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Attachment;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository {

    Attachment save(@Nonnull Attachment attachment);

    List<Attachment> findAll();

    Optional<Attachment> findById(@Nonnull String id);

    void deleteById(@Nonnull String id);

}
