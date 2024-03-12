package com.kalgooksoo.board.controller;

import com.kalgooksoo.board.domain.Category;
import com.kalgooksoo.board.command.CreateCategoryCommand;
import com.kalgooksoo.board.model.HierarchicalCategory;
import com.kalgooksoo.board.command.UpdateCategoryCommand;
import com.kalgooksoo.board.service.CategoryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * 카테고리 REST 컨트롤러
 */
@Tag(name = "CategoryRestController", description = "카테고리 API")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<EntityModel<Category>> create(@Valid @RequestBody CreateCategoryCommand command) {
        Category menu = categoryService.create(command);

        ResponseEntity<EntityModel<Category>> invocationValue = methodOn(this.getClass())
                .findById(menu.getId());

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<Category> entityModel = EntityModel.of(menu, link);
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<HierarchicalCategory>>> findAll() {
        List<EntityModel<HierarchicalCategory>> entityModels = categoryService.findAll()
                .stream()
                .map(hierarchicalCategory -> {
                    ResponseEntity<EntityModel<Category>> invocationValue = methodOn(this.getClass())
                            .findById(hierarchicalCategory.id());

                    Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                            .withRel("self");

                    return EntityModel.of(hierarchicalCategory, link);
                })
                .toList();

        ResponseEntity<CollectionModel<EntityModel<HierarchicalCategory>>> invocationValue = methodOn(this.getClass())
                .findAll();

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        CollectionModel<EntityModel<HierarchicalCategory>> collectionModel = CollectionModel.of(entityModels, link);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Category>> findById(
            @Parameter(description = "카테고리 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        Category menu = categoryService.findById(id).orElseThrow(() -> new NoSuchElementException("카테고리가 존재하지 않습니다"));

        ResponseEntity<CollectionModel<EntityModel<HierarchicalCategory>>> invocationValue = methodOn(this.getClass())
                .findAll();

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<Category> entityModel = EntityModel.of(menu, link);
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Category>> updateById(
            @Parameter(description = "카테고리 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id,
            @Valid @RequestBody UpdateCategoryCommand command
    ) {
        Category menu = categoryService.update(id, command);

        ResponseEntity<EntityModel<Category>> invocationValue = methodOn(this.getClass())
                .findById(menu.getId());

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<Category> entityModel = EntityModel.of(menu, link);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "카테고리 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}