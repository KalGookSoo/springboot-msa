package com.kalgooksoo.menu.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 메뉴 트리
 *
 * @param id         식별자
 * @param name       이름
 * @param url        URL
 * @param children   하위 메뉴
 * @param createdBy  생성자
 * @param createdAt  생성 일시
 * @param modifiedAt 수정 일시
 */
public record HierarchicalMenu(
        String id,
        String name,
        String url,
        List<HierarchicalMenu> children,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static HierarchicalMenu of(String id, String name, String url, List<HierarchicalMenu> children, String createdBy, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new HierarchicalMenu(id, name, url, children, createdBy, createdAt, modifiedAt);
    }
}