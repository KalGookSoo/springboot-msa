package com.kalgooksoo.acl.repository;

import com.kalgooksoo.acl.model.AclEntry;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class AclEntryJpaRepository implements AclEntryRepository {

    private final EntityManager em;

    @Override
    public AclEntry save(@Nonnull AclEntry aclEntry) {
        try {
            em.persist(aclEntry);
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            return em.merge(aclEntry);
        }
        return aclEntry;
    }
}
