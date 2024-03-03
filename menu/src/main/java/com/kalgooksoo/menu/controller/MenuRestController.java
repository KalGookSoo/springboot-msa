package com.kalgooksoo.menu.controller;

import com.kalgooksoo.menu.command.MenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.service.MenuService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CollectionModel<EntityModel<Menu>>> findAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Menu>> findById(
            @Parameter(description = "메뉴 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Menu>> updateById() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById() {
        return null;
    }

}