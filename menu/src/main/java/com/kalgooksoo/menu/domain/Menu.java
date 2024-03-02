package com.kalgooksoo.menu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

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

}