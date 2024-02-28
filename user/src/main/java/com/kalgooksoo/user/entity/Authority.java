package com.kalgooksoo.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

/**
 * 권한
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = {"id"})
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_authority")
@DynamicInsert
public class Authority {

    /**
     * 권한 식별자
     */
    @Id
    private String id;

    /**
     * 계정 식별자
     */
    private String userId;

    /**
     * 이름
     */
    private String name;

    private Authority(String id, String userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    public static Authority create(String userId, String name) {
        if (!name.startsWith("ROLE_")) {
            throw new IllegalArgumentException("name must start with 'ROLE_'\n name: " + name);
        }
        return new Authority(UUID.randomUUID().toString(), userId, name);
    }
}