package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateAttachmentCommand;
import com.kalgooksoo.board.domain.Attachment;

import java.util.List;

public interface AttachmentService {

    Attachment create(CreateAttachmentCommand command);

    void delete(String id);

    List<Attachment> findAllByArticleId(String articleId);

}
