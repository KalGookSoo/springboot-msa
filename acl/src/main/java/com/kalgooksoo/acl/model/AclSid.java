package com.kalgooksoo.acl.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import static lombok.AccessLevel.PROTECTED;

/**
 * 보안 주체
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_acl_sid")
@DynamicInsert
public class AclSid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean principal;

    private String sid;

}