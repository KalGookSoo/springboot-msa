package com.kalgooksoo.vote.controller;

import com.kalgooksoo.vote.service.VoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 투표 REST 컨트롤러
 */
@Tag(name = "VoteRestController", description = "투표 API")
@RestController
@RequestMapping("/votes")
@RequiredArgsConstructor
public class VoteRestController {

    private final VoteService voteService;

}