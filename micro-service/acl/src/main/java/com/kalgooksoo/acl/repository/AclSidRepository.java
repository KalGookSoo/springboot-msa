package com.kalgooksoo.acl.repository;

import com.kalgooksoo.acl.model.AclSid;
import jakarta.annotation.Nonnull;

/**
 * 보안 주체 저장소
 */
public interface AclSidRepository {

    AclSid save(@Nonnull AclSid aclSid);

}
