package com.kalgooksoo.vote.repository;

import com.kalgooksoo.vote.domain.Vote;
import com.kalgooksoo.vote.domain.VoteId;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VoteMemoryRepository implements VoteRepository {

    private final List<Vote> votes = new ArrayList<>();

    @Override
    public Vote save(@Nonnull Vote vote) {
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
    public List<Vote> findAllByReferenceId(@Nonnull String referenceId) {
        return votes.stream()
                .filter(vote -> vote.getId().getReferenceId().equals(referenceId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByReferenceId(@Nonnull String referenceId) {
        votes.removeIf(vote -> vote.getId().getReferenceId().equals(referenceId));
    }

    @Override
    public void deleteById(@Nonnull VoteId id) {
        votes.removeIf(vote -> vote.getId().equals(id));
    }

}
