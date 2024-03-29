package com.kalgooksoo.acl.service;

import com.kalgooksoo.acl.model.AclClass;
import com.kalgooksoo.acl.model.AclObjectIdentity;
import com.kalgooksoo.acl.model.AclSid;
import com.kalgooksoo.acl.repository.AclClassRepository;
import com.kalgooksoo.acl.repository.AclEntryRepository;
import com.kalgooksoo.acl.repository.AclObjectIdentityRepository;
import com.kalgooksoo.acl.repository.AclSidRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * 접근제어목록 서비스
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultAclService {

    private final AclClassRepository aclClassRepository;

    private final AclEntryRepository aclEntryRepository;

    private final AclObjectIdentityRepository aclObjectIdentityRepository;

    private final AclSidRepository aclSidRepository;

    public AclObjectIdentity findObjectIdentity(Serializable identifier) {
        return aclObjectIdentityRepository.findByObjectIdIdentity(identifier.toString())
                .orElseThrow(() -> new IllegalArgumentException("Not found object identity: " + identifier));
    }

    public AclClass findAclClass(@Nonnull Long id) {
        return aclClassRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found acl class: " + id));
    }

    public AclSid saveAclSid(AclSid aclSid) {
        return aclSidRepository.save(aclSid);
    }

    public AclClass findAclClassByClassIdType(String classIdType) {
        return aclClassRepository.findByClassIdType(classIdType)
                .orElseThrow(() -> new IllegalArgumentException("Not found acl class: " + classIdType));
    }
}
