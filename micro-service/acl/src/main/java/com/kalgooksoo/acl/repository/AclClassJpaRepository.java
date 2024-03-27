package com.kalgooksoo.acl.repository;

import com.kalgooksoo.acl.model.AclClass;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class AclClassJpaRepository implements AclClassRepository {

    private final EntityManager em;

    @Override
    public AclClass save(@Nonnull AclClass aclClass) {
        try {
            em.persist(aclClass);
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            return em.merge(aclClass);
        }
        return aclClass;
    }

    @Override
    public Optional<AclClass> findById(@Nonnull Long id) {
        return Optional.ofNullable(em.find(AclClass.class, id));
    }

}
