package com.kalgooksoo.acl.controller;

import com.kalgooksoo.acl.command.CreateAclEntryCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 접근제어목록 REST 컨트롤러
 */
@Tag(name = "AclRestController", description = "접근제어목록 API")
@RestController
@RequestMapping("/acl")
@RequiredArgsConstructor
public class AclRestController {

    private final MutableAclService mutableAclService;


    @PostMapping
    public ResponseEntity<?> createAclEntry(@Valid @RequestBody CreateAclEntryCommand command) {
        // 객체에 대한 ACL 객체 식별자를 생성합니다.
        ObjectIdentity objectId = new ObjectIdentityImpl(command.className(), command.identifier());
        MutableAcl acl = mutableAclService.createAcl(objectId);

        // createdBy 사용자에 대한 SID를 생성합니다.
        Sid ownerSid = new PrincipalSid(command.principal());

        // createdBy 사용자에게 객체에 대한 관리 권한을 부여합니다.
        Permission permission = switch (command.mask()) {
            case 1 -> BasePermission.READ;
            case 2 -> BasePermission.WRITE;
            case 4 -> BasePermission.CREATE;
            case 8 -> BasePermission.DELETE;
            case 16 -> BasePermission.ADMINISTRATION;
            default -> throw new IllegalArgumentException("Invalid mask value");
        };
        acl.insertAce(acl.getEntries().size(), permission, ownerSid, true);

//        ResponseEntity<EntityModel<MutableAcl>> invocationValue = methodOn(this.getClass())
//                .findById(acl.getId());
//
//        Link link = WebMvcLinkBuilder.linkTo(invocationValue)
//                .withRel("self");

//        EntityModel<MutableAcl> entityModel = EntityModel.of(acl, link);

        // 데이터베이스의 ACL을 업데이트합니다.
        mutableAclService.updateAcl(acl);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(acl);
    }



}