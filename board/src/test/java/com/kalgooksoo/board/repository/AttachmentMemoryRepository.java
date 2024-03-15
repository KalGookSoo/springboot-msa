package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Attachment;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class AttachmentMemoryRepository implements AttachmentRepository {

    private final List<Attachment> attachments = new ArrayList<>();

    @Override
    public Attachment save(Attachment attachment) {
        Assert.notNull(attachment, "첨부파일은 NULL이 될 수 없습니다");
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
    public List<Attachment> findAll() {
        return attachments;
    }

    @Override
    public Optional<Attachment> findById(String id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        return attachments.stream()
                .filter(attachment -> attachment.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        attachments.stream()
                .filter(attachment -> attachment.getId().equals(id))
                .findFirst()
                .map(attachments::remove)
                .orElseThrow(() -> new NoSuchElementException("첨부파일을 찾을 수 없습니다."));
    }

}
