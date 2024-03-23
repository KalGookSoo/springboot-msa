package com.kalgooksoo.view.repository;

import com.kalgooksoo.view.domain.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 뷰 저장소 테스트
 */
@DataJpaTest
@ActiveProfiles("test")
class ViewRepositoryTest {

    private ViewRepository viewRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup() {
        viewRepository = new ViewJpaRepository(entityManager.getEntityManager());
    }

    @Test
    @DisplayName("뷰를 저장합니다. 성공 시 뷰를 반환합니다.")
    void saveShouldReturnView() {
        // Given
        View view = new View(UUID.randomUUID().toString(), "anonymous");

        // When
        View savedView = viewRepository.save(view);

        // Then
        assertNotNull(savedView);
    }

    @Test
    @DisplayName("참조 식별자로 모든 뷰를 조회합니다. 성공 시 뷰 목록을 반환합니다.")
    void findAllByReferenceIdShouldReturnViews() {
        // Given
        View view = new View(UUID.randomUUID().toString(), "anonymous");
        View savedView = viewRepository.save(view);

        // When
        List<View> views = viewRepository.findAllByReferenceId(savedView.getId().getReferenceId());

        // Then
        assertFalse(views.isEmpty());
    }

    @Test
    @DisplayName("참조 식별자로 모든 뷰를 조회합니다. 실패 시 빈 목록을 반환합니다.")
    void findAllByReferenceIdShouldReturnEmptyList() {
        // Given
        String referenceId = UUID.randomUUID().toString();

        // When
        var views = viewRepository.findAllByReferenceId(referenceId);

        // Then
        assertTrue(views.isEmpty());
    }

    @Test
    @DisplayName("참조 식별자로 모든 뷰를 삭제합니다. 성공 시 삭제된 뷰를 조회할 수 없습니다.")
    void deleteAllByReferenceId() {
        // Given
        View view = new View(UUID.randomUUID().toString(), "anonymous");
        View savedView = viewRepository.save(view);

        // When
        String referenceId = savedView.getId().getReferenceId();
        viewRepository.deleteAllByReferenceId(referenceId);

        // Then
        List<View> views = viewRepository.findAllByReferenceId(referenceId);
        assertTrue(views.isEmpty());
    }

}