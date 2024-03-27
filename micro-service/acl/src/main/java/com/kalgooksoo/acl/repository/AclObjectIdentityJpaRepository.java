package com.kalgooksoo.acl.repository;

import com.kalgooksoo.acl.model.AclObjectIdentity;
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
public class AclObjectIdentityJpaRepository implements AclObjectIdentityRepository {

    private final EntityManager em;

    @Override
    public AclObjectIdentity save(@Nonnull AclObjectIdentity aclObjectIdentity) {
        try {
            em.persist(aclObjectIdentity);
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            return em.merge(aclObjectIdentity);
        }
        return aclObjectIdentity;
    }

    @Override
    public Optional<AclObjectIdentity> findByObjectIdIdentity(@Nonnull String objectIdIdentity) {
        String jpql = "SELECT aoi FROM AclObjectIdentity aoi WHERE aoi.objectIdIdentity = :objectIdIdentity";
        return em.createQuery(jpql, AclObjectIdentity.class)
                .setParameter("objectIdIdentity", objectIdIdentity)
                .getResultList()
                .stream()
                .findFirst();
    }

}
