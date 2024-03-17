package com.kalgooksoo.user.controller;

import com.kalgooksoo.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.command.CreateUserCommand;
import com.kalgooksoo.user.command.SignInCommand;
import com.kalgooksoo.user.command.UpdateUserCommand;
import com.kalgooksoo.user.command.UpdateUserPasswordCommand;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.model.UserSummary;
import com.kalgooksoo.user.search.UserSearch;
import com.kalgooksoo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * 계정 REST 컨트롤러
 */
@Tag(name = "UserRestController", description = "계정 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    /**
     * 계정 생성
     *
     * @param command 계정 생성 명령
     * @return 생성된 계정
     */
    @Operation(summary = "계정 생성", description = "계정을 생성합니다")
    @PostMapping
    public ResponseEntity<EntityModel<User>> create(
            @Parameter(schema = @Schema(implementation = CreateUserCommand.class)) @Valid @RequestBody CreateUserCommand command
    ) throws UsernameAlreadyExistsException {

        User user = userService.createUser(command);

        ResponseEntity<EntityModel<User>> invocationValue = methodOn(this.getClass())
                .findById(user.getId());

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<User> entityModel = EntityModel.of(user, link);
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    /**
     * 계정 목록 조회
     *
     * @param search 검색 조건
     * @return 계정 목록
     */
    @Operation(summary = "계정 목록 조회", description = "계정 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<User>>> findAll(
            UserSearch search
    ) {
        Page<User> page = userService.findAll(search, search.pageable());

        List<EntityModel<User>> entityModels = page.getContent()
                .stream()
                .map(user -> {
                    ResponseEntity<EntityModel<User>> invocationValue = methodOn(this.getClass())
                            .findById(user.getId());

                    Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                            .withRel("self");

                    return EntityModel.of(user, link);
                })
                .collect(Collectors.toList());

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());

        ResponseEntity<PagedModel<EntityModel<User>>> invocationValue = methodOn(this.getClass())
                .findAll(search);

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        PagedModel<EntityModel<User>> pagedModel = PagedModel.of(entityModels, metadata, link);
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * 계정 조회
     *
     * @param id 계정 식별자
     * @return 계정
     */
    @Operation(summary = "계정 조회", description = "계정을 조회합니다")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User>> findById(
            @Parameter(description = "계정 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        User user = userService.findById(id).orElseThrow(() -> new NoSuchElementException("계정을 찾을 수 없습니다."));

        ResponseEntity<EntityModel<User>> invocationValue = methodOn(this.getClass())
                .findById(id);

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<User> entityModel = EntityModel.of(user, link);
        return ResponseEntity.ok(entityModel);
    }

    /**
     * 계정 수정
     *
     * @param id      계정 식별자
     * @param command 계정 수정 명령
     * @return 수정된 계정
     */
    @Operation(summary = "계정 수정", description = "계정을 수정합니다")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<User>> updateById(
            @Parameter(schema = @Schema(type = "string", format = "uuid")) @PathVariable String id,
            @Parameter(schema = @Schema(implementation = UpdateUserCommand.class)) @Valid @RequestBody UpdateUserCommand command
    ) {
        User user = userService.update(id, command);

        ResponseEntity<EntityModel<User>> invocationValue = methodOn(this.getClass())
                .findById(id);

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<User> entityModel = EntityModel.of(user, link);
        return ResponseEntity.ok(entityModel);
    }

    /**
     * 계정 삭제
     * TODO 삭제 시 응답 본문 포맷을 공통으로 만들어 볼 것
     *
     * @param id 계정 식별자
     */
    @Operation(summary = "계정 삭제", description = "계정을 삭제합니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 계정 패스워드 수정
     *
     * @param id      계정 식별자
     * @param command 패스워드 수정 명령
     * @return 수정된 계정
     */
    @Operation(summary = "계정 패스워드 수정", description = "계정 패스워드를 수정합니다")
    @PutMapping("/{id}/password")
    public ResponseEntity<EntityModel<User>> updatePassword(
            @Parameter(schema = @Schema(type = "string", format = "uuid")) @PathVariable String id,
            @Parameter(schema = @Schema(implementation = UpdateUserPasswordCommand.class)) @Valid @RequestBody UpdateUserPasswordCommand command
    ) {
        userService.updatePassword(id, command.originPassword(), command.newPassword());
        return findById(id);
    }

    /**
     * 계정 검증
     *
     * @param command 계정 검증 명령
     * @return 계정
     */
    @Operation(summary = "계정 검증", description = "계정을 검증합니다")
    @PostMapping("/sign-in")
    public ResponseEntity<EntityModel<UserSummary>> signIn(
            @Parameter(schema = @Schema(implementation = SignInCommand.class)) @Valid @RequestBody SignInCommand command
    ) {
        UserSummary userSummary = userService.verify(command.username(), command.password());

        ResponseEntity<EntityModel<UserSummary>> invocationValue = methodOn(this.getClass())
                .signIn(command);

        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
                .withRel("self");

        EntityModel<UserSummary> entityModel = EntityModel.of(userSummary, link);
        return ResponseEntity.ok(entityModel);
    }

}