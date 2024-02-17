package com.kalgooksoo.user.controller;

import com.kalgooksoo.user.command.CreateUserCommand;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.search.UserSearch;
import com.kalgooksoo.user.service.UserService;
import com.kalgooksoo.user.value.ContactNumber;
import com.kalgooksoo.user.value.Email;
import io.swagger.v3.oas.annotations.Parameter;
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

    @PostMapping
    public ResponseEntity<EntityModel<User>> create(@Valid @RequestBody CreateUserCommand command) {
        Email email = new Email(command.emailId(), command.emailDomain());
        ContactNumber contactNumber = new ContactNumber(command.firstContactNumber(), command.middleContactNumber(), command.lastContactNumber());
        User user = User.create(command.username(), command.password(), command.name(), email, contactNumber);
        try {
            User createdEntity = userService.create(user);
            EntityModel<User> resource = EntityModel.of(createdEntity);
            WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).findById(createdEntity.getId()));
            resource.add(linkTo.withRel("self"));
            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (UsernameAlreadyExistsException e) {
            throw new UsernameAlreadyExistsException("계정이 이미 존재합니다");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User>> findById(@Parameter(description = "계정 ID", schema = @Schema(type = "string", format = "uuid")) @PathVariable String id) {
        Optional<User> foundEntity = userService.findById(id);
        if (foundEntity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<User> resource = EntityModel.of(foundEntity.get());
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).findById(id));
        resource.add(linkTo.withRel("self"));
        return ResponseEntity.ok(resource);
    }


}
