package com.kalgooksoo.acl.repository;

import com.kalgooksoo.acl.model.AclSid;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class AclSidJpaRepository implements AclSidRepository {

    private final EntityManager em;

    @Override
    public AclSid save(@Nonnull AclSid aclSid) {
        try {
            em.persist(aclSid);
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            return em.merge(aclSid);
        }
        return aclSid;
    }

}
