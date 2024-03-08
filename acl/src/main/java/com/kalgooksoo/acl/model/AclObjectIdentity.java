package com.kalgooksoo.acl.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.acls.model.ObjectIdentity;

import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

/**
 * 객체 식별자
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@ToString
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_acl_object_identity")
@DynamicInsert
public class AclObjectIdentity implements ObjectIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long ownerSid;

    private Long objectIdClass;

    private String objectIdIdentity;

    private Long parentObject;

    private Boolean entriesInheriting;

    /**
     * org.springframework.security.acls.domain#getIdentifier
     */
    @Override
    public Serializable getIdentifier() {
        return null;
    }

    /**
     * org.springframework.security.acls.domain#getType
     */
    @Override
    public String getType() {
        return null;
    }

}
