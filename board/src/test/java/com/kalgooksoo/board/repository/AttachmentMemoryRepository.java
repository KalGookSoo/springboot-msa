package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Attachment;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class AttachmentMemoryRepository implements AttachmentRepository {

    private final List<Attachment> attachments = new ArrayList<>();

    @Override
    public Attachment save(@Nonnull Attachment attachment) {
        if (attachment.getId() == null) {
            attachments.add(attachment);
        } else {
            attachments.stream()
                    .filter(a -> a.getId().equals(attachment.getId()))
                    .findFirst()
                    .map(a -> attachment)
                    .orElseGet(() -> {
                        attachments.add(attachment);
                        return attachment;
                    });
        }
        return attachment;
    }

    @Override
    public List<Attachment> findAllByReferenceId(@Nonnull String referenceId) {
        return attachments.stream()
                .filter(attachment -> attachment.getReferenceId().equals(referenceId))
                .toList();
    }

    @Override
    public Optional<Attachment> findById(@Nonnull String id) {
        return attachments.stream()
                .filter(attachment -> attachment.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(@Nonnull String id) {
        attachments.stream()
                .filter(attachment -> attachment.getId().equals(id))
                .findFirst()
                .map(attachments::remove)
                .orElseThrow(() -> new NoSuchElementException("첨부파일을 찾을 수 없습니다."));
    }

}
