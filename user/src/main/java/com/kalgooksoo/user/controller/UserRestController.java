package com.kalgooksoo.user.controller;

import com.kalgooksoo.security.command.SignInCommand;
import com.kalgooksoo.security.model.UserPrincipal;
import com.kalgooksoo.user.command.CreateUserCommand;
import com.kalgooksoo.user.command.UpdateUserCommand;
import com.kalgooksoo.user.command.UpdateUserPasswordCommand;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.search.UserSearch;
import com.kalgooksoo.user.service.UserService;
import com.kalgooksoo.user.value.ContactNumber;
import com.kalgooksoo.user.value.Email;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    @PostMapping
    public ResponseEntity<EntityModel<User>> create(@Valid @RequestBody CreateUserCommand command) throws UsernameAlreadyExistsException {
        Email email = new Email(command.emailId(), command.emailDomain());
        ContactNumber contactNumber = new ContactNumber(command.firstContactNumber(), command.middleContactNumber(), command.lastContactNumber());
        User user = User.create(command.username(), command.password(), command.name(), email, contactNumber);
        try {
            User createdEntity = userService.createUser(user);
            EntityModel<User> resource = EntityModel.of(createdEntity);
            WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).findById(createdEntity.getId()));
            resource.add(linkTo.withRel("self"));
            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (UsernameAlreadyExistsException e) {
            throw new UsernameAlreadyExistsException(command.username(), "계정이 이미 존재합니다");
        }
    }

    /**
     * 계정 목록 조회
     *
     * @param search 검색 조건
     * @return 계정 목록
     */
    @Operation(parameters = {
            @Parameter(
                    in = ParameterIn.HEADER,
                    name = "Authorization",
                    required = true,
                    description = "Bearer ${token}",
                    schema = @Schema(type = "string")
            )
    })
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<User>>> findAll(UserSearch search) {
        Page<User> page = userService.findAll(search, search.pageable());

        List<EntityModel<User>> users = page.getContent().stream()
                .map(user -> EntityModel.of(user,
                        WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).findById(user.getId())).withRel("self")))
                .collect(Collectors.toList());

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        PagedModel<EntityModel<User>> pagedModel = PagedModel.of(users, metadata);

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).findAll(search));
        pagedModel.add(linkTo.withRel("self"));

        return ResponseEntity.ok(pagedModel);
    }

    /**
     * 계정 조회
     *
     * @param id 계정 식별자
     * @return 계정
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User>> findById(
            @Parameter(description = "계정 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        Optional<User> foundEntity = userService.findById(id);
        if (foundEntity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<User> resource = EntityModel.of(foundEntity.get());
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).findById(id));
        resource.add(linkTo.withRel("self"));
        return ResponseEntity.ok(resource);
    }

    /**
     * 계정 수정
     *
     * @param id      계정 식별자
     * @param command 계정 수정 명령
     * @return 수정된 계정
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<User>> updateById(
            @Parameter(description = "계정 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id,
            @Valid @RequestBody UpdateUserCommand command
    ) {
        User updatedEntity = userService.update(id, command);
        EntityModel<User> resource = EntityModel.of(updatedEntity);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).findById(id));
        resource.add(linkTo.withRel("self"));
        return ResponseEntity.ok(resource);
    }

    /**
     * 계정 삭제
     * TODO 삭제 시 응답 본문 포맷을 공통으로 만들어 볼 것
     *
     * @param id 계정 식별자
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "계정 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id
    ) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 계정 패스워드 수정
     *
     * @param id      계정 식별자
     * @param command 패스워드 수정 명령
     * @return 수정된 계정
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<EntityModel<User>> updatePassword(
            @Parameter(description = "계정 식별자", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id,
            @Valid @RequestBody UpdateUserPasswordCommand command
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
    @PostMapping("/sign-in")
    public ResponseEntity<EntityModel<UserPrincipal>> signIn(@Valid @RequestBody SignInCommand command) {
        UserPrincipal verifiedUser = userService.verify(command.username(), command.password());
        EntityModel<UserPrincipal> resource = EntityModel.of(verifiedUser);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).signIn(command));
        resource.add(linkTo.withRel("self"));
        return ResponseEntity.ok(resource);
    }

}