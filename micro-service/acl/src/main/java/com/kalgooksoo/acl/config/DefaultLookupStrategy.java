package com.kalgooksoo.acl.config;

import jakarta.persistence.EntityManager;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;

import java.util.List;
import java.util.Map;

/**
 * @see org.springframework.security.acls.jdbc.BasicLookupStrategy
 */
public class DefaultLookupStrategy implements LookupStrategy {

    private final EntityManager entityManager;

    private final AclCache aclCache;

    public DefaultLookupStrategy(EntityManager entityManager, AclCache aclCache, AclAuthorizationStrategy aclAuthorizationStrategy, ConsoleAuditLogger consoleAuditLogger) {
        this.entityManager = entityManager;
        this.aclCache = aclCache;
    }

    @Override
    public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects, List<Sid> sids) {
        return null;
    }

}