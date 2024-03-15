package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Vote;
import com.kalgooksoo.board.domain.VoteId;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VoteMemoryRepository implements VoteRepository {

    private final List<Vote> votes = new ArrayList<>();

    @Override
    public Vote save(Vote vote) {
        Assert.notNull(vote, "투표는 NULL이 될 수 없습니다");
        if (vote.getId() == null) {
            votes.add(vote);
        } else {
            votes.stream()
                    .filter(v -> v.getId().equals(vote.getId()))
                    .findFirst()
                    .map(v -> vote)
                    .orElseGet(() -> {
                        votes.add(vote);
                        return vote;
                    });
        }
        return vote;
    }

    @Override
    public List<Vote> findAllByReferenceId(String referenceId) {
        Assert.notNull(referenceId, "참조 식별자는 NULL이 될 수 없습니다");
        return votes.stream()
                .filter(vote -> vote.getId().getReferenceId().equals(referenceId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByReferenceId(String referenceId) {
        Assert.notNull(referenceId, "참조 식별자는 NULL이 될 수 없습니다");
        votes.removeIf(vote -> vote.getId().getReferenceId().equals(referenceId));
    }

    @Override
    public void deleteById(VoteId id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        votes.removeIf(vote -> vote.getId().equals(id));
    }

}
