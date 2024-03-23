package com.kalgooksoo.board.service;

import com.kalgooksoo.board.repository.VoteRepository;
import com.kalgooksoo.core.principal.PrincipalProvider;
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

}