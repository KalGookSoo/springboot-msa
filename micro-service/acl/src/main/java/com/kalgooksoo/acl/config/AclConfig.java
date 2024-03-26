package com.kalgooksoo.acl.config;

import com.kalgooksoo.core.principal.PrincipalProvider;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;

@Configuration
@RequiredArgsConstructor
public class AclConfig {

    private final EntityManager entityManager;

    private final PrincipalProvider principalProvider;

    @Bean
    public MutableAclService mutableAclService() {
        return new DefaultMutableAclService(entityManager, lookupStrategy(), aclCache(), principalProvider);
    }

    @Bean
    public LookupStrategy lookupStrategy() {
        return new DefaultLookupStrategy(entityManager, aclCache(), aclAuthorizationStrategy(), consoleAuditLogger());
    }

    @Bean
    public AclCache aclCache() {
        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache("aclCache");
        return new SpringCacheBasedAclCache(concurrentMapCache, permissionGrantingStrategy(), aclAuthorizationStrategy());
    }

    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy() {
        return new DefaultPermissionGrantingStrategy(consoleAuditLogger());
    }

    @Bean
    public ConsoleAuditLogger consoleAuditLogger() {
        return new ConsoleAuditLogger();
    }

    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new DefaultAclAuthorizationStrategy();
    }

}