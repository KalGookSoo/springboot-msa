package com.kalgooksoo.acl.repository;

import com.kalgooksoo.acl.model.AclEntry;
import jakarta.annotation.Nonnull;

/**
 * 접근제어목록 저장소
 */
public interface AclEntryRepository {

    AclEntry save(@Nonnull AclEntry aclEntry);

}
