package com.kalgooksoo.vote.service;

import com.kalgooksoo.vote.command.CreateVoteCommand;
import com.kalgooksoo.vote.domain.Vote;
import com.kalgooksoo.vote.domain.VoteId;
import com.kalgooksoo.vote.domain.VoteType;
import com.kalgooksoo.vote.repository.VoteRepository;
import com.kalgooksoo.core.principal.PrincipalProvider;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @see VoteService
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultVoteService implements VoteService {

    private final VoteRepository voteRepository;

    private final PrincipalProvider principalProvider;

    @Override
    public Vote create(@Nonnull CreateVoteCommand command) {
        String voter = principalProvider.getUsername();
        Vote vote = new Vote(command.referenceId(), voter, VoteType.valueOf(command.voteType()));
        return voteRepository.save(vote);
    }

    @Override
    public void delete(@Nonnull String referenceId) {
        String voter = principalProvider.getUsername();
        VoteId voteId = new VoteId(referenceId, voter);
        voteRepository.deleteById(voteId);
    }

}
