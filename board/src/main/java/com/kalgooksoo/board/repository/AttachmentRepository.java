package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Attachment;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository {

    Attachment save(Attachment attachment);

    List<Attachment> findAll();

    Optional<Attachment> findById(String id);

    void deleteById(String id);

}
