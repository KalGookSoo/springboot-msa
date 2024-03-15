package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Vote;
import com.kalgooksoo.board.domain.VoteId;

import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote);

    List<Vote> findAllByReferenceId(String referenceId);

    void deleteAllByReferenceId(String referenceId);

    void deleteById(VoteId id);

}
