package com.kalgooksoo.acl.service;

import com.kalgooksoo.acl.command.createAclEntryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.AbstractPermission;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultAclService implements AclService {

    private final MutableAclService mutableAclService;

    @Override
    public void createAclEntry(createAclEntryCommand command) {
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

        // 데이터베이스의 ACL을 업데이트합니다.
        mutableAclService.updateAcl(acl);
    }
}