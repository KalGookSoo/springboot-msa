package com.kalgooksoo.attachment.service;

import com.kalgooksoo.attachment.command.CreateAttachmentCommand;
import com.kalgooksoo.attachment.domain.Attachment;
import jakarta.annotation.Nonnull;

import java.util.List;

public interface AttachmentService {

    Attachment create(@Nonnull CreateAttachmentCommand command);

    void delete(@Nonnull String id);

    List<Attachment> findAllByArticleId(@Nonnull String articleId);

}