package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Vote;
import com.kalgooksoo.board.domain.VoteId;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * 투표 저장소
 */
public interface VoteRepository {

    Vote save(@Nonnull Vote vote);

    List<Vote> findAllByReferenceId(@Nonnull String referenceId);

    void deleteAllByReferenceId(@Nonnull String referenceId);

    void deleteById(@Nonnull VoteId id);

}
