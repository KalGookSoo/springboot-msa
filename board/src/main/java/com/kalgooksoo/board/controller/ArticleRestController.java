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
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
            @Parameter(schema = @Schema(implementation = CreateArticleCommand.class)) @Valid @RequestBody CreateArticleCommand command
    ) {
        Article article = articleService.create(command);

        ResponseEntity<EntityModel<Article>> invocationValue = methodOn(this.getClass())
                .findById(article.getId());

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<Article> entityModel = EntityModel.of(article, link);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(entityModel);
    }

    @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Article>>> findAll(
            @Parameter(schema = @Schema(implementation = ArticleSearch.class)) ArticleSearch search
    ) {
        Page<Article> page = articleService.search(search);
        List<EntityModel<Article>> entityModels = page.getContent()
                .stream()
                .map(article -> {
                    ResponseEntity<EntityModel<Article>> invocationValue = methodOn(this.getClass())
                            .findById(article.getId());

                    Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                            .withRel("self");

                    return EntityModel.of(article, link);
                })
                .toList();

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());

        ResponseEntity<PagedModel<EntityModel<Article>>> invocationValue = methodOn(this.getClass())
                .findAll(search);

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        PagedModel<EntityModel<Article>> pagedModel = PagedModel.of(entityModels, metadata, link);

        return ResponseEntity.ok()
                .body(pagedModel);
    }

    @Operation(summary = "게시글 조회", description = "게시글을 조회합니다")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Article>> findById(
            @Parameter(description = "게시글 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        Article article = articleService.findById(id);

        ResponseEntity<EntityModel<Article>> invocationValue = methodOn(this.getClass())
                .findById(article.getId());

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<Article> entityModel = EntityModel.of(article, link);
        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Article>> update(
            @Parameter(description = "게시글 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id,
            @Parameter(schema = @Schema(implementation = UpdateArticleCommand.class)) @Valid @RequestBody UpdateArticleCommand command
    ) {
        Article article = articleService.update(id, command);

        ResponseEntity<EntityModel<Article>> invocationValue = methodOn(this.getClass())
                .findById(article.getId());

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<Article> entityModel = EntityModel.of(article, link);
        return ResponseEntity.ok().body(entityModel);
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "게시글 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "게시글 이동", description = "게시글을 이동합니다")
    @PutMapping("/{id}/move")
    public ResponseEntity<EntityModel<Article>> move(
            @Parameter(description = "게시글 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id,
            @Parameter(schema = @Schema(implementation = MoveArticleCommand.class)) @Valid @RequestBody MoveArticleCommand command
    ) {
        Article article = articleService.move(id, command);

        ResponseEntity<EntityModel<Article>> invocationValue = methodOn(this.getClass())
                .findById(article.getId());

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<Article> entityModel = EntityModel.of(article, link);
        return ResponseEntity.ok().body(entityModel);
    }

}
