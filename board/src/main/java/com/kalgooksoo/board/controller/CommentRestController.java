package com.kalgooksoo.board.controller;

import com.kalgooksoo.board.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CommentRestController", description = "댓글 API")
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

}