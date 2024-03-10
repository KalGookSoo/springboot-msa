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

    /**
     * 카테고리를 생성합니다.
     * 
     * @param command 카테고리 생성 커맨드
     * @return 카테고리
     */
    public static Category create(CreateCategoryCommand command) {
        Assert.notNull(command, "카테고리 생성 커맨드는 필수입니다.");
        Category category = new Category();
        category.id = UUID.randomUUID().toString();
        category.parentId = command.parentId();
        category.name = command.name();
        category.type = CategoryType.valueOf(command.type());
        category.createdBy = command.createdBy();
        category.createdAt = LocalDateTime.now();
        return category;
    }

    /**
     * 카테고리를 수정합니다.
     *
     * @param command 카테고리 수정 커맨드
     */
    public void update(UpdateCategoryCommand command) {
        this.parentId = command.parentId();
        this.name = command.name();
        this.type = CategoryType.valueOf(command.type());
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