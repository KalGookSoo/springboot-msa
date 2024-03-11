package com.kalgooksoo.board.domain;

import com.kalgooksoo.board.model.CreateCategoryCommand;
import com.kalgooksoo.board.model.UpdateCategoryCommand;
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
        Category category = new Category();
        Assert.notNull(createdBy, "생성자는 필수입니다.");
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

    public void moveTo(String parentId) {
        this.parentId = parentId;
        this.modifiedAt = LocalDateTime.now();
    }

    /**
     * 최상위 메뉴 여부를 반환합니다.
     *
     * @return 최상위 메뉴 여부
     */
    public boolean isRoot() {
        return parentId == null;
    }

}