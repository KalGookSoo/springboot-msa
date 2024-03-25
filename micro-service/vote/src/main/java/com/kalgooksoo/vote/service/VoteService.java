package com.kalgooksoo.vote.service;

import com.kalgooksoo.vote.command.CreateVoteCommand;
import com.kalgooksoo.vote.domain.Vote;
import jakarta.annotation.Nonnull;

/**
 * 투표 서비스
 */
public interface VoteService {

    Vote create(@Nonnull CreateVoteCommand command);

    void delete(@Nonnull String referenceId);

}