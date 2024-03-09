package com.kalgooksoo.board.model;

import com.kalgooksoo.board.domain.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface HierarchicalCategoryFactory {

    static HierarchicalCategory toHierarchical(Category category, Map<String, List<Category>> categoryMap) {
        List<Category> children = categoryMap.getOrDefault(category.getId(), new ArrayList<>());
        return HierarchicalCategory.of(
                category.getId(),
                children.stream().map(child -> toHierarchical(child, categoryMap)).toList(),
                category.getName(),
                category.getType(),
                category.getCreatedBy(),
                category.getCreatedAt(),
                category.getModifiedAt()
        );
    }

}