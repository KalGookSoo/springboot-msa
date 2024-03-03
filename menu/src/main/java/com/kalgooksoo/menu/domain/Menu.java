package com.kalgooksoo.menu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

/**
 * 메뉴
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_menu")
@DynamicInsert
public class Menu {

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
     * URL
     */
    private String url;

    /**
     * 부모 식별자
     */
    private String parentId;

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
     * 최상위 메뉴 여부를 반환합니다.
     *
     * @return 최상위 메뉴 여부
     */
    public boolean isRoot() {
        return parentId == null;
    }

    public static Menu createRoot(String name, String url, String createdBy) {
        Assert.notNull(createdBy, "생성자는 null이 될 수 없습니다");
        Menu menu = new Menu();
        menu.id = UUID.randomUUID().toString();
        menu.name = name;
        menu.url = url;
        menu.createdBy = createdBy;
        menu.createdAt = LocalDateTime.now();
        return menu;
    }

    public static Menu createChild(String name, String url, String parentId, String createdBy) {
        Assert.notNull(createdBy, "생성자는 null이 될 수 없습니다");
        Menu menu = new Menu();
        menu.id = UUID.randomUUID().toString();
        menu.name = name;
        menu.url = url;
        menu.parentId = parentId;
        menu.createdBy = createdBy;
        menu.createdAt = LocalDateTime.now();
        return menu;
    }

    public void update(String name, String url) {
        this.name = name;
        this.url = url;
        this.modifiedAt = LocalDateTime.now();
    }

}