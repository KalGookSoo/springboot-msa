package com.kalgooksoo.board.model;

import com.kalgooksoo.board.domain.CategoryType;

import java.time.LocalDateTime;
import java.util.List;

public record HierarchicalCategory(
        String id,
        List<HierarchicalCategory> children,
        String name,
        CategoryType type,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static HierarchicalCategory of(String id, List<HierarchicalCategory> children, String name, CategoryType type, String createdBy, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new HierarchicalCategory(id, children, name, type, createdBy, createdAt, modifiedAt);
    }
}