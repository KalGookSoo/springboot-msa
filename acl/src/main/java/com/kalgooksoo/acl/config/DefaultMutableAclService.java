package com.kalgooksoo.acl.config;

import jakarta.persistence.EntityManager;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.*;

import java.util.List;
import java.util.Map;

/**
 * @see org.springframework.security.acls.jdbc.JdbcMutableAclService
 */
public class DefaultMutableAclService implements MutableAclService {

    private final EntityManager entityManager;

    private final LookupStrategy lookupStrategy;

    private final AclCache aclCache;

    public DefaultMutableAclService(EntityManager entityManager, LookupStrategy lookupStrategy, AclCache aclCache) {
        this.entityManager = entityManager;
        this.lookupStrategy = lookupStrategy;
        this.aclCache = aclCache;
    }

    @Override
    public MutableAcl createAcl(ObjectIdentity objectIdentity) throws AlreadyExistsException {
        return null;
    }

    @Override
    public void deleteAcl(ObjectIdentity objectIdentity, boolean deleteChildren) throws ChildrenExistException {

    }

    @Override
    public MutableAcl updateAcl(MutableAcl acl) throws NotFoundException {
        return null;
    }

    @Override
    public List<ObjectIdentity> findChildren(ObjectIdentity parentIdentity) {
        return null;
    }

    @Override
    public Acl readAclById(ObjectIdentity object) throws NotFoundException {
        return null;
    }

    @Override
    public Acl readAclById(ObjectIdentity object, List<Sid> sids) throws NotFoundException {
        return null;
    }

    @Override
    public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects) throws NotFoundException {
        return null;
    }

    @Override
    public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects, List<Sid> sids) throws NotFoundException {
        return null;
    }

}