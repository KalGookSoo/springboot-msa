package com.kalgooksoo.acl.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import static lombok.AccessLevel.PROTECTED;

/**
 * 도메인
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_acl_class")
@DynamicInsert
public class AclClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String className;

    private String classIdType;

}