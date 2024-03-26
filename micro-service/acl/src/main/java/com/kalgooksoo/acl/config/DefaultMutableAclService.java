package com.kalgooksoo.acl.config;

import com.kalgooksoo.acl.model.AclClass;
import com.kalgooksoo.acl.model.AclObjectIdentity;
import com.kalgooksoo.acl.model.AclSid;
import com.kalgooksoo.core.principal.PrincipalProvider;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.*;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @see org.springframework.security.acls.jdbc.JdbcMutableAclService
 */
@RequiredArgsConstructor
public class DefaultMutableAclService implements MutableAclService {

    private final EntityManager entityManager;

    private final LookupStrategy lookupStrategy;

    private final AclCache aclCache;

    private final PrincipalProvider principalProvider;

    /**
     * @see org.springframework.security.acls.jdbc.JdbcMutableAclService#createAcl(ObjectIdentity)
     *
     * @param objectIdentity 객체 식별자
     * @return MutableAcl
     * @throws AlreadyExistsException 이미 존재하는 경우
     */
    @Override
    public MutableAcl createAcl(ObjectIdentity objectIdentity) throws AlreadyExistsException {
        Assert.notNull(objectIdentity, "Object Identity required");
        if (this.retrieveObjectIdentityPrimaryKey(objectIdentity) != null) {
            throw new AlreadyExistsException("Object identity '" + objectIdentity + "' already exists");
        } else {
            String sid = principalProvider.getUsername();
            AclSid aclSid = new AclSid(true, sid);
            this.createObjectIdentity(objectIdentity, aclSid);
            Acl acl = this.readAclById(objectIdentity);
            Assert.isInstanceOf(MutableAcl.class, acl, "MutableAcl should be been returned");
            return (MutableAcl)acl;
        }
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

    /**
     * 객체 식별자와 도메인이 있으면 객체 식별자의 식별자를 반환합니다.
     *
     * @param oid 객체 식별자
     * @return 객체 식별자의 식별자
     */
    protected Long retrieveObjectIdentityPrimaryKey(ObjectIdentity oid) {
        Serializable identifier = oid.getIdentifier();
        try {
            AclObjectIdentity aclObjectIdentity = findObjectIdentity(identifier)
                    .orElseThrow(NoSuchElementException::new);

            findAclClass(aclObjectIdentity.getObjectIdClass())
                    .orElseThrow(NoSuchElementException::new);

            return aclObjectIdentity.getId();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    protected void createObjectIdentity(ObjectIdentity object, Sid owner) {
//        Long sidId = this.createOrRetrieveSidPrimaryKey(owner, true);
//        Long classId = this.createOrRetrieveClassPrimaryKey(object.getType(), true, object.getIdentifier().getClass());
//        this.jdbcOperations.update(this.insertObjectIdentity, new Object[]{classId, object.getIdentifier().toString(), sidId, Boolean.TRUE});
    }

    private Optional<AclObjectIdentity> findObjectIdentity(Serializable identifier) {
        AclObjectIdentity aclObjectIdentity = entityManager.find(AclObjectIdentity.class, identifier);
        return Optional.ofNullable(aclObjectIdentity);
    }

    private Optional<AclClass> findAclClass(Serializable identifier) {
        AclClass aclClass = entityManager.find(AclClass.class, identifier);
        return Optional.ofNullable(aclClass);
    }

}