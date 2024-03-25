package com.kalgooksoo.menu.controller;

import com.kalgooksoo.menu.command.CreateMenuCommand;
import com.kalgooksoo.menu.command.MoveMenuCommand;
import com.kalgooksoo.menu.command.UpdateMenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    @Operation(summary = "메뉴 API 옵션", description = "메뉴 API의 옵션을 제공합니다")
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        HttpHeaders httpHeaders = new HttpHeaders();
        Set<HttpMethod> httpMethods = Set.of(HttpMethod.GET, HttpMethod.POST);
        httpHeaders.setAllow(httpMethods);
        return ResponseEntity.ok().headers(httpHeaders).build();
    }

    @Operation(summary = "메뉴 생성", description = "메뉴를 생성합니다")
    @PostMapping
    public ResponseEntity<EntityModel<Menu>> create(
            @Parameter(description = "메뉴 생성 명령", schema = @Schema(implementation = CreateMenuCommand.class)) @Valid @RequestBody CreateMenuCommand command
    ) {
        Menu menu = menuService.create(command);

        ResponseEntity<EntityModel<Menu>> invocationValue = methodOn(this.getClass())
                .findById(menu.getId());

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<Menu> entityModel = EntityModel.of(menu, link);
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @Operation(summary = "메뉴 목록 조회", description = "메뉴 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Menu>>> findAll() {
        List<EntityModel<Menu>> entityModels = menuService.findAll()
                .stream()
                .map(menu -> {
                    ResponseEntity<EntityModel<Menu>> invocationValue = methodOn(this.getClass())
                            .findById(menu.getId());

                    Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                            .withRel("self");

                    return EntityModel.of(menu, link);
                })
                .toList();

        ResponseEntity<CollectionModel<EntityModel<Menu>>> invocationValue = methodOn(this.getClass())
                .findAll();

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        CollectionModel<EntityModel<Menu>> collectionModel = CollectionModel.of(entityModels, link);
        return ResponseEntity.ok(collectionModel);
    }

    @Operation(summary = "메뉴 조회", description = "메뉴를 조회합니다")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Menu>> findById(
            @Parameter(description = "메뉴 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        Menu menu = menuService.findById(id);

        ResponseEntity<EntityModel<Menu>> invocationValue = methodOn(this.getClass())
                .findById(id);

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<Menu> entityModel = EntityModel.of(menu, link);
        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "메뉴 수정", description = "메뉴를 수정합니다")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Menu>> updateById(
            @Parameter(description = "메뉴 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id,
            @Parameter(description = "메뉴 수정 커맨드", schema = @Schema(implementation = UpdateMenuCommand.class)) @Valid @RequestBody UpdateMenuCommand command
    ) {
        Menu menu = menuService.update(id, command);

        ResponseEntity<EntityModel<Menu>> invocationValue = methodOn(this.getClass())
                .findById(menu.getId());

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<Menu> entityModel = EntityModel.of(menu, link);
        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "메뉴 삭제", description = "메뉴를 삭제합니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "메뉴 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "메뉴 이동", description = "메뉴를 이동합니다")
    @PutMapping("/{id}/move")
    public ResponseEntity<EntityModel<Menu>> moveById(
            @Parameter(description = "메뉴 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id,
            @Parameter(description = "메뉴 이동 커맨드", schema = @Schema(implementation = MoveMenuCommand.class)) @Valid @RequestBody MoveMenuCommand command
    ) {
        Menu menu = menuService.move(id, command);

        ResponseEntity<EntityModel<Menu>> invocationValue = methodOn(this.getClass())
                .findById(menu.getId());

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<Menu> entityModel = EntityModel.of(menu, link);
        return ResponseEntity.ok(entityModel);
    }

}