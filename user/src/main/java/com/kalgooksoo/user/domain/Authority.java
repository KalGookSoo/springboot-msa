package com.kalgooksoo.user.domain;

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

@Entity
@Table(name = "tb_authority")
@DynamicInsert
public class Authority {

    @Id
    private String id;

    /**
     * 이름
     */
    private String name;

    private Authority(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Authority create(String name) {
        // name이 ROLE_로 시작하지 않는다면 예외를 발생시킵니다.
        if (!name.startsWith("ROLE_")) {
            throw new IllegalArgumentException("name must start with 'ROLE_'");
        }
        return new Authority(UUID.randomUUID().toString(), name);
    }
}