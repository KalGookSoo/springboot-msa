package com.kalgooksoo.acl.config;

import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.model.Acl;

/**
 * @see org.springframework.security.acls.domain.AclAuthorizationStrategyImpl
 */
public class DefaultAclAuthorizationStrategy implements AclAuthorizationStrategy {

    @Override
    public void securityCheck(Acl acl, int changeType) {

    }

}