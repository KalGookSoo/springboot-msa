package com.kalgooksoo.vote.service;

import com.kalgooksoo.core.principal.PrincipalProvider;
import com.kalgooksoo.core.principal.StubPrincipalProvider;
import com.kalgooksoo.vote.command.CreateVoteCommand;
import com.kalgooksoo.vote.domain.Vote;
import com.kalgooksoo.vote.domain.VoteType;
import com.kalgooksoo.vote.repository.VoteMemoryRepository;
import com.kalgooksoo.vote.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 투표 서비스 테스트
 */
class VoteServiceTest {

    private VoteService voteService;

    @BeforeEach
    void setUp() {
        VoteRepository voteRepository = new VoteMemoryRepository();
        PrincipalProvider principalProvider = new StubPrincipalProvider();
        voteService = new DefaultVoteService(voteRepository, principalProvider);
    }

    @Test
    @DisplayName("투표를 생성합니다. 성공 시 투표를 반환합니다.")
    void createShoudReturnVote() {
        // Given
        String referenceId = UUID.randomUUID().toString();
        String voteType = VoteType.APPROVE.name();
        CreateVoteCommand createVoteCommand = new CreateVoteCommand(referenceId, voteType);

        // When
        Vote vote = voteService.create(createVoteCommand);

        // Then
        assertNotNull(vote);
    }

    @Test
    @DisplayName("투표를 삭제합니다.")
    void deleteShouldNotThrowException() {
        // Given
        String referenceId = UUID.randomUUID().toString();
        String voteType = VoteType.APPROVE.name();
        CreateVoteCommand createVoteCommand = new CreateVoteCommand(referenceId, voteType);
        Vote vote = voteService.create(createVoteCommand);

        // When & Then
        assertDoesNotThrow(() -> voteService.delete(vote.getId().getReferenceId()));
    }
    
}