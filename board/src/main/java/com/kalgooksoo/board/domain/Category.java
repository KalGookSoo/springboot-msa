package com.kalgooksoo.board.domain;

import com.kalgooksoo.core.hierarchy.Hierarchical;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

/**
 * 카테고리
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_category")
@DynamicInsert
public class Category extends Hierarchical<Category, String> {

    /**
     * 식별자
     */
    @Id
    private String id;

    /**
     * 상위 카테고리 식별자
     */
    private String parentId;

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

    public static Category create(String parentId, String name, String type, String createdBy) {
        Assert.notNull(createdBy, "생성자는 필수입니다.");
        Category category = new Category();
        category.id = UUID.randomUUID().toString();
        category.parentId = parentId;
        category.name = name;
        category.type = CategoryType.valueOf(type);
        category.createdBy = createdBy;
        category.createdAt = LocalDateTime.now();
        return category;
    }

    public void update(String name, String type) {
        this.name = name;
        this.type = CategoryType.valueOf(type);
        this.modifiedAt = LocalDateTime.now();
    }

    @Override
    public boolean isRoot() {
        return parentId == null;
    }

    @Override
    public void moveTo(String parentId) {
        this.parentId = parentId;
        this.modifiedAt = LocalDateTime.now();
    }

}