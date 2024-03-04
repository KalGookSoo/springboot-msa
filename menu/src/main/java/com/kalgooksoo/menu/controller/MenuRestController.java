package com.kalgooksoo.menu.controller;

import com.kalgooksoo.menu.command.MenuCommand;
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
    public ResponseEntity<EntityModel<Menu>> create(@Valid @RequestBody MenuCommand command) {
        Menu menu = Menu.create(command.name(), command.url(), command.parentId(), command.createdBy());
        menuService.create(menu);
        EntityModel<Menu> entityModel = EntityModel.of(menu);
        MenuRestController menuRestController = methodOn(this.getClass());
        WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(menuRestController.findById(menu.getId()));
        entityModel.add(webMvcLinkBuilder.withRel("self"));
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<HierarchicalMenu>>> findAll() {
        List<HierarchicalMenu> menus = menuService.findAll();
        List<EntityModel<HierarchicalMenu>> entityModels = menus.stream()
                .map(hierarchicalMenu -> {
                    MenuRestController menuRestController = methodOn(this.getClass());
                    ResponseEntity<EntityModel<Menu>> invocationValue = menuRestController.findById(hierarchicalMenu.id());
                    WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(invocationValue);
                    Link link = webMvcLinkBuilder.withRel("self");
                    return EntityModel.of(hierarchicalMenu, link);
                })
                .toList();
        CollectionModel<EntityModel<HierarchicalMenu>> collectionModel = CollectionModel.of(entityModels);
        MenuRestController menuRestController = methodOn(this.getClass());
        ResponseEntity<CollectionModel<EntityModel<HierarchicalMenu>>> invocationValue = menuRestController.findAll();
        WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(invocationValue);
        Link link = webMvcLinkBuilder.withRel("self");
        collectionModel.add(link);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Menu>> findById(
            @Parameter(description = "메뉴 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        Menu menu = menuService.findById(id).orElseThrow(() -> new NoSuchElementException("메뉴가 존재하지 않습니다"));
        EntityModel<Menu> entityModel = EntityModel.of(menu);
        MenuRestController menuRestController = methodOn(this.getClass());
        ResponseEntity<CollectionModel<EntityModel<HierarchicalMenu>>> invocationValue = menuRestController.findAll();
        WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(invocationValue);
        entityModel.add(webMvcLinkBuilder.withRel("self"));
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Menu>> updateById(
            @Parameter(description = "메뉴 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id,
            @Valid @RequestBody MenuCommand command
    ) {
        Menu menu = menuService.update(id, command);
        EntityModel<Menu> entityModel = EntityModel.of(menu);
        MenuRestController menuRestController = methodOn(this.getClass());
        ResponseEntity<EntityModel<Menu>> invocationValue = menuRestController.findById(menu.getId());
        WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(invocationValue);
        entityModel.add(webMvcLinkBuilder.withRel("self"));
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "메뉴 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        menuService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}