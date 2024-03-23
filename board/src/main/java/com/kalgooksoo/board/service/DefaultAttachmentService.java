package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateAttachmentCommand;
import com.kalgooksoo.board.domain.Attachment;
import com.kalgooksoo.board.repository.AttachmentRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @see AttachmentService
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultAttachmentService implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Override
    public Attachment create(@Nonnull CreateAttachmentCommand command) {
        Attachment attachment = Attachment.create(command.referenceId(), command.name(), command.pathName(), command.mimeType(), command.size());
        return attachmentRepository.save(attachment);
    }

    @Override
    public void delete(@Nonnull String id) {
        attachmentRepository.deleteById(id);
    }

    @Override
    public List<Attachment> findAllByArticleId(@Nonnull String articleId) {
        return attachmentRepository.findAllByReferenceId(articleId);
    }

}