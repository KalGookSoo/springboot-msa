package com.kalgooksoo.acl.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.acls.model.Sid;

import static lombok.AccessLevel.PROTECTED;

/**
 * 보안 주체
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@ToString
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_acl_sid")
@DynamicInsert
public class AclSid implements Sid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean principal;

    private String sid;

}
