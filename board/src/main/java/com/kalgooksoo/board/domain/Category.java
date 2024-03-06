package com.kalgooksoo.board.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

/**
 * 카테고리
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_category")
@DynamicInsert
public class Category {

    /**
     * 식별자
     */
    @Id
    private String id;

    /**
     * 이름
     */
    private String name;

    /**
     * 타입
     */
    @Enumerated(EnumType.STRING)
    private CategoryType type;

    /**
     * 생성자
     */
    private String createdBy;

    /**
     * 생성 일시
     */
    private LocalDateTime createdAt;

    /**
     * 수정 일시
     */
    private LocalDateTime modifiedAt;

    /**
     * 카테고리를 생성합니다.
     *
     * @param name 이름
     * @param type 타입
     * @param createdBy 생성자
     * @return 카테고리
     */
    public static Category create(String name, CategoryType type, String createdBy) {
        Category category = new Category();
        category.name = name;
        category.type = type;
        category.createdBy = createdBy;
        category.createdAt = LocalDateTime.now();
        return category;
    }

    /**
     * 카테고리를 수정합니다.
     *
     * @param name 이름
     * @param type 타입
     */
    public void update(String name, CategoryType type) {
        this.name = name;
        this.type = type;
        this.modifiedAt = LocalDateTime.now();
    }

}