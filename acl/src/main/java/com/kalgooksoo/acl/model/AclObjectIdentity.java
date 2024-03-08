package com.kalgooksoo.acl.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import static lombok.AccessLevel.PROTECTED;

/**
 * 객체 식별자
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_acl_object_identity")
@DynamicInsert
public class AclObjectIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long ownerSid;

    private Long objectIdClass;

    private String objectIdIdentity;

    private Long parentObject;

    private Boolean entriesInheriting;

}