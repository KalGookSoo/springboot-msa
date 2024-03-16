package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Vote;
import com.kalgooksoo.board.domain.VoteId;

import java.util.List;

/**
 * 투표 저장소
 */
public interface VoteRepository {

    Vote save(Vote vote);

    List<Vote> findAllByReferenceId(String referenceId);

    void deleteAllByReferenceId(String referenceId);

    void deleteById(VoteId id);

}
