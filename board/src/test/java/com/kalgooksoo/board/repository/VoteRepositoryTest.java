package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Vote;
import com.kalgooksoo.board.domain.VoteId;
import com.kalgooksoo.board.domain.VoteType;
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

@DataJpaTest
@ActiveProfiles("test")
class VoteRepositoryTest {

    private VoteRepository voteRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup() {
        voteRepository = new VoteJpaRepository(entityManager.getEntityManager());
    }

    @Test
    @DisplayName("투표를 저장합니다. 성공 시 투표를 반환합니다.")
    void saveShouldReturnVote() {
        // Given
        Vote vote = new Vote(UUID.randomUUID().toString(), "anonymous", VoteType.APPROVE);

        // When
        Vote savedVote = voteRepository.save(vote);

        // Then
        assertNotNull(savedVote);
    }

    @Test
    @DisplayName("투표를 저장합니다. 실패 시 IllegalArgumentException을 던집니다.")
    void saveShouldThrowIllegalArgumentException() {
        // Given
        Vote vote = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> voteRepository.save(vote));
    }

    @Test
    @DisplayName("참조 식별자로 모든 투표를 조회합니다. 성공 시 투표 목록을 반환합니다.")
    void findAllByReferenceIdShouldReturnVotes() {
        // Given
        Vote vote = new Vote(UUID.randomUUID().toString(), "anonymous", VoteType.APPROVE);
        Vote savedVote = voteRepository.save(vote);

        // When
        List<Vote> votes = voteRepository.findAllByReferenceId(savedVote.getId().getReferenceId());

        // Then
        assertFalse(votes.isEmpty());
    }

    @Test
    @DisplayName("참조 식별자로 모든 투표를 조회합니다. 실패 시 빈 목록을 반환합니다.")
    void findAllByReferenceIdShouldReturnEmptyList() {
        // Given
        String referenceId = UUID.randomUUID().toString();

        // When
        var votes = voteRepository.findAllByReferenceId(referenceId);

        // Then
        assertTrue(votes.isEmpty());
    }

    @Test
    @DisplayName("참조 식별자로 모든 투표를 삭제합니다. 성공 시 삭제된 투표를 조회할 수 없습니다.")
    void deleteAllByReferenceId() {
        // Given
        Vote vote = new Vote(UUID.randomUUID().toString(), "anonymous", VoteType.APPROVE);
        Vote savedVote = voteRepository.save(vote);

        // When
        String referenceId = savedVote.getId().getReferenceId();
        voteRepository.deleteAllByReferenceId(referenceId);

        // Then
        List<Vote> votes = voteRepository.findAllByReferenceId(referenceId);
        assertTrue(votes.isEmpty());
    }

    @Test
    @DisplayName("참조 식별자로 모든 투표를 삭제합니다. 실패 시 IllegalArgumentException을 던집니다.")
    void deleteAllByReferenceIdShouldThrowIllegalArgumentException() {
        // Given
        String referenceId = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> voteRepository.deleteAllByReferenceId(referenceId));
    }

    @Test
    @DisplayName("투표를 삭제합니다. 성공 시 삭제된 투표를 조회할 수 없습니다.")
    void deleteAllById() {
        // Given
        Vote vote = new Vote(UUID.randomUUID().toString(), "anonymous", VoteType.APPROVE);
        Vote savedVote = voteRepository.save(vote);

        // When
        voteRepository.deleteById(savedVote.getId());

        // Then
        List<Vote> votes = voteRepository.findAllByReferenceId(savedVote.getId().getReferenceId());
        assertTrue(votes.isEmpty());
    }

    @Test
    @DisplayName("투표를 삭제합니다. 실패 시 IllegalArgumentException을 던집니다.")
    void deleteAllByIdShouldThrowIllegalArgumentException() {
        // Given
        VoteId id = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> voteRepository.deleteById(id));
    }

}