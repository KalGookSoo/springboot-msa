package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateAttachmentCommand;
import com.kalgooksoo.board.domain.Attachment;
import jakarta.annotation.Nonnull;

import java.util.List;

public interface AttachmentService {

    Attachment create(@Nonnull CreateAttachmentCommand command);

    void delete(@Nonnull String id);

    List<Attachment> findAllByArticleId(@Nonnull String articleId);

}