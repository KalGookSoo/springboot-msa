package com.kalgooksoo.board.controller;

import com.kalgooksoo.board.command.CreateArticleCommand;
import com.kalgooksoo.board.command.MoveArticleCommand;
import com.kalgooksoo.board.command.UpdateArticleCommand;
import com.kalgooksoo.board.domain.Article;
import com.kalgooksoo.board.search.ArticleSearch;
import com.kalgooksoo.board.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 게시글 REST 컨트롤러
 */
@Tag(name = "ArticleRestController", description = "게시글 API")
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleRestController {

    private final ArticleService articleService;

    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다")
    @PostMapping
    public ResponseEntity<EntityModel<Article>> create(
            @Parameter(description = "게시글 생성 명령", schema = @Schema(implementation = CreateArticleCommand.class)) @Valid @RequestBody CreateArticleCommand command
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(null);
    }

    @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<CollectionModel<Article>> findAllByCategoryId(
            @Parameter(description = "게시글 검색 조건", schema = @Schema(implementation = ArticleSearch.class)) ArticleSearch search
    ) {
        return ResponseEntity.ok()
                .body(null);
    }

    @Operation(summary = "게시글 조회", description = "게시글을 조회합니다")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Article>> findById(
            @Parameter(description = "게시글 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        return ResponseEntity.ok()
                .body(null);
    }

    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Article>> update(
            @Parameter(description = "게시글 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id,
            @Parameter(description = "게시글 수정 명령") @Valid @RequestBody UpdateArticleCommand command
    ) {
        return ResponseEntity.ok()
                .body(null);
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "게시글 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        return ResponseEntity.noContent()
                .build();
    }

    @Operation(summary = "게시글 이동", description = "게시글을 이동합니다")
    @PutMapping("/{id}/move")
    public ResponseEntity<EntityModel<Article>> move(
            @Parameter(description = "게시글 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id,
            @Parameter(description = "게시글 이동 명령", schema = @Schema(implementation = MoveArticleCommand.class)) @Valid @RequestBody MoveArticleCommand command
    ) {
        return ResponseEntity.ok()
                .body(null);
    }

}
