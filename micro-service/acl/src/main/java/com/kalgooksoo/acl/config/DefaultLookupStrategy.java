package com.kalgooksoo.acl.config;

import com.kalgooksoo.acl.service.DefaultAclService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class DefaultLookupStrategy implements LookupStrategy {

    private final DefaultAclService defaultAclService;

    private final AclCache aclCache;

    private final AclAuthorizationStrategy aclAuthorizationStrategy;

    private final ConsoleAuditLogger consoleAuditLogger;

    @Override
    public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects, List<Sid> sids) {
        return null;
    }

}