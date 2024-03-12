package com.kalgooksoo.menu.controller;

import com.kalgooksoo.menu.command.CreateMenuCommand;
import com.kalgooksoo.menu.command.UpdateMenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.model.HierarchicalMenu;
import com.kalgooksoo.menu.service.MenuService;
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
 * 메뉴 REST 컨트롤러
 */
@Tag(name = "MenuRestController", description = "메뉴 API")
@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuRestController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<EntityModel<Menu>> create(@Valid @RequestBody CreateMenuCommand command) {
        Menu menu = menuService.create(command);

        ResponseEntity<EntityModel<Menu>> invocationValue = methodOn(this.getClass())
                .findById(menu.getId());

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<Menu> entityModel = EntityModel.of(menu, link);
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<HierarchicalMenu>>> findAll() {
        List<EntityModel<HierarchicalMenu>> entityModels = menuService.findAll()
                .stream()
                .map(hierarchicalMenu -> {
                    ResponseEntity<EntityModel<Menu>> invocationValue = methodOn(this.getClass())
                            .findById(hierarchicalMenu.id());

                    Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                            .withRel("self");

                    return EntityModel.of(hierarchicalMenu, link);
                })
                .toList();

        ResponseEntity<CollectionModel<EntityModel<HierarchicalMenu>>> invocationValue = methodOn(this.getClass())
                .findAll();

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        CollectionModel<EntityModel<HierarchicalMenu>> collectionModel = CollectionModel.of(entityModels, link);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Menu>> findById(
            @Parameter(description = "메뉴 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        Menu menu = menuService.findById(id).orElseThrow(() -> new NoSuchElementException("메뉴가 존재하지 않습니다"));

        ResponseEntity<CollectionModel<EntityModel<HierarchicalMenu>>> invocationValue = methodOn(this.getClass())
                .findAll();

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<Menu> entityModel = EntityModel.of(menu, link);
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Menu>> updateById(
            @Parameter(description = "메뉴 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id,
            @Valid @RequestBody UpdateMenuCommand command
    ) {
        Menu menu = menuService.update(id, command);

        ResponseEntity<EntityModel<Menu>> invocationValue = methodOn(this.getClass())
                .findById(menu.getId());

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<Menu> entityModel = EntityModel.of(menu, link);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "메뉴 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }

}