package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Attachment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class AttachmentRepositoryTest {

    private AttachmentRepository attachmentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup() {
        attachmentRepository = new AttachmentJpaRepository(entityManager.getEntityManager());
    }

    @Test
    @DisplayName("첨부파일을 저장합니다. 성공 시 첨부파일을 반환합니다.")
    void saveShouldReturnAttachment() {
        // Given
        Attachment attachment = Attachment.create(UUID.randomUUID().toString(), "이미지", "/attachments/{categoryId}/image.png", "image/png", 100L);

        // When
        Attachment savedAttachment = attachmentRepository.save(attachment);

        // Then
        assertNotNull(savedAttachment);
    }

    @Test
    @DisplayName("첨부파일을 저장합니다. 실패 시 IllegalArgumentException을 던집니다.")
    void saveShouldThrowIllegalArgumentException() {
        // Given
        Attachment attachment = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> attachmentRepository.save(attachment));
    }

    @Test
    @DisplayName("모든 첨부파일을 조회합니다. 성공 시 첨부파일 목록을 반환합니다.")
    void findAllShouldReturnAttachments() {
        // Given
        Attachment attachment = Attachment.create(UUID.randomUUID().toString(), "이미지", "/attachments/{categoryId}/image.png", "image/png", 100L);
        Attachment savedAttachment = attachmentRepository.save(attachment);

        // When
        List<Attachment> attachments = attachmentRepository.findAll();

        // Then
        assertNotNull(attachments);
        assertTrue(attachments.stream().anyMatch(c -> c.getId().equals(savedAttachment.getId())));
    }

    @Test
    @DisplayName("모든 첨부파일을 조회합니다. 실패 시 빈 목록을 반환합니다.")
    void findAllShouldReturnEmptyList() {
        // When
        List<Attachment> attachments = attachmentRepository.findAll();

        // Then
        assertNotNull(attachments);
        assertTrue(attachments.isEmpty());
    }

    @Test
    @DisplayName("첨부파일을 조회합니다. 성공 시 첨부파일을 반환합니다.")
    void findByIdShouldReturnAttachment() {
        // Given
        Attachment attachment = Attachment.create(UUID.randomUUID().toString(), "이미지", "/attachments/{categoryId}/image.png", "image/png", 100L);
        Attachment savedAttachment = attachmentRepository.save(attachment);

        // When
        Optional<Attachment> foundAttachment = attachmentRepository.findById(savedAttachment.getId());

        // Then
        assertTrue(foundAttachment.isPresent());
    }

    @Test
    @DisplayName("첨부파일을 조회합니다. 실패 시 빈 Optional을 반환합니다.")
    void findByIdShouldReturnEmptyOptional() {
        // Given
        String id = UUID.randomUUID().toString();

        // When
        Optional<Attachment> foundAttachment = attachmentRepository.findById(id);

        // Then
        assertTrue(foundAttachment.isEmpty());
    }

    @Test
    @DisplayName("첨부파일을 삭제합니다. 성공 시 삭제된 첨부파일을 조회할 수 없습니다.")
    void deleteTest() {
        // Given
        Attachment attachment = Attachment.create(UUID.randomUUID().toString(), "이미지", "/attachments/{categoryId}/image.png", "image/png", 100L);
        Attachment savedAttachment = attachmentRepository.save(attachment);

        // When
        attachmentRepository.deleteById(savedAttachment.getId());

        // Then
        Optional<Attachment> deletedAttachment = attachmentRepository.findById(savedAttachment.getId());
        assertTrue(deletedAttachment.isEmpty());
    }

    @Test
    @DisplayName("첨부파일을 삭제합니다. 실패 시 IllegalArgumentException을 던집니다.")
    void deleteShouldThrowIllegalArgumentException() {
        // Given
        String id = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> attachmentRepository.deleteById(id));
    }

    @Test
    @DisplayName("첨부파일을 삭제합니다. 없는 첨부파일을 삭제 시 NoSuchElementException을 던집니다.")
    void deleteShouldThrowNoSuchElementException() {
        // Given
        String id = UUID.randomUUID().toString();

        // When & Then
        assertThrows(NoSuchElementException.class, () -> attachmentRepository.deleteById(id));
    }

}