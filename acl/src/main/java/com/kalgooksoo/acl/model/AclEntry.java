package com.kalgooksoo.acl.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AuditableAccessControlEntry;
import org.springframework.security.acls.model.Permission;

import static lombok.AccessLevel.PROTECTED;

/**
 * 접근제어목록
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@ToString
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_acl_entry")
@DynamicInsert
public class AclEntry implements AccessControlEntry, AuditableAccessControlEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long aclObjectIdentity;

    private Long sid;

    private Integer aceOrder;

    private Integer mask;

    private Boolean granting;

    private Boolean auditSuccess;

    private Boolean auditFailure;

    /**
     * @see org.springframework.security.acls.domain.AccessControlEntryImpl#getAcl()
     */
    @Override
    public Acl getAcl() {
        return null;
    }

    /**
     * @see org.springframework.security.acls.domain.AccessControlEntryImpl#getPermission()
     */
    @Override
    public Permission getPermission() {
        return null;
    }

    /**
     * @see org.springframework.security.acls.domain.AccessControlEntryImpl#isGranting()
     */
    @Override
    public boolean isGranting() {
        return false;
    }

    /**
     * @see org.springframework.security.acls.domain.AccessControlEntryImpl#isAuditFailure()
     */
    @Override
    public boolean isAuditFailure() {
        return false;
    }

    /**
     * @see org.springframework.security.acls.domain.AccessControlEntryImpl#isAuditSuccess()
     */
    @Override
    public boolean isAuditSuccess() {
        return false;
    }

}
