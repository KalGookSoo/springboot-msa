package com.kalgooksoo.acl.config;

import com.kalgooksoo.acl.model.AclClass;
import com.kalgooksoo.acl.model.AclObjectIdentity;
import com.kalgooksoo.acl.model.AclSid;
import com.kalgooksoo.acl.service.DefaultAclService;
import com.kalgooksoo.core.principal.PrincipalProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.*;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see org.springframework.security.acls.jdbc.JdbcMutableAclService
 */
@RequiredArgsConstructor
public class DefaultMutableAclService implements MutableAclService {

    private final DefaultAclService defaultAclService;

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
            AclObjectIdentity aclObjectIdentity = defaultAclService.findObjectIdentity(identifier);

            AclClass aclClass = defaultAclService.findAclClass(aclObjectIdentity.getObjectIdClass());

            return aclObjectIdentity.getId();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * @see org.springframework.security.acls.jdbc.JdbcMutableAclService
     */
    protected void createObjectIdentity(ObjectIdentity object, Sid owner) {
        Long sidId = this.createOrRetrieveSidPrimaryKey(owner, true);
//        Long classId = this.createOrRetrieveClassPrimaryKey(object.getType(), true, object.getIdentifier().getClass());
//        this.jdbcOperations.update(this.insertObjectIdentity, new Object[]{classId, object.getIdentifier().toString(), sidId, Boolean.TRUE});
    }

    /**
     * acl_sid에서 기본 키를 검색하며, 필요한 경우 새로운 행을 생성하고 allowCreate 속성이 true인 경우 생성이 허용됩니다.
     *
     *
     * @param sid 찾거나 생성할 Sid
     * @param allowCreate 찾을 수 없는 경우 생성이 허용되면 true
     * @return 기본 키 또는 찾을 수 없는 경우 null
     * @throws IllegalArgumentException Sid가 인식되지 않는 구현인 경우
     */
    protected Long createOrRetrieveSidPrimaryKey(Sid sid, boolean allowCreate) {
        Assert.notNull(sid, "Sid required");

        if (!allowCreate) {
            return null;
        }

        if (sid instanceof AclSid aclSid) {
            return defaultAclService.saveAclSid(aclSid).getId();
        }
        if (sid instanceof GrantedAuthoritySid grantedAuthoritySid) {
            String sidName = grantedAuthoritySid.getGrantedAuthority();
            AclSid aclSid = new AclSid(false, sidName);
            return defaultAclService.saveAclSid(aclSid).getId();
        }
        throw new IllegalArgumentException("Unsupported implementation of Sid");
    }

}