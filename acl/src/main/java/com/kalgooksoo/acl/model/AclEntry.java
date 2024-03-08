package com.kalgooksoo.acl.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import static lombok.AccessLevel.PROTECTED;

/**
 * 접근제어목록
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_acl_entry")
@DynamicInsert
public class AclEntry {

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

}