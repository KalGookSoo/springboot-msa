package com.kalgooksoo.acl.repository;

import com.kalgooksoo.acl.model.AclClass;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * 도메인 저장소
 */
public interface AclClassRepository {

    AclClass save(@Nonnull AclClass aclClass);

    Optional<AclClass> findById(@Nonnull Long id);

    Optional<AclClass> findByClassIdType(@Nonnull String classIdType);

}
