package com.kalgooksoo.acl.repository;

import com.kalgooksoo.acl.model.AclObjectIdentity;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * 객체 식별자 저장소
 */
public interface AclObjectIdentityRepository {

    AclObjectIdentity save(@Nonnull AclObjectIdentity aclObjectIdentity);

    Optional<AclObjectIdentity> findByObjectIdIdentity(@Nonnull String objectIdIdentity);

}
