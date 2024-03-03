package com.kalgooksoo.menu.model;

import com.kalgooksoo.menu.domain.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MenuHierarchyFactory {

    static HierarchicalMenu toHierarchical(Menu menu, Map<String, List<Menu>> menuMap) {
        List<Menu> children = menuMap.getOrDefault(menu.getId(), new ArrayList<>());
        return HierarchicalMenu.of(
                menu.getId(),
                menu.getName(),
                menu.getUrl(),
                children.stream().map(child -> toHierarchical(child, menuMap)).toList(),
                menu.getCreatedBy(),
                menu.getCreatedAt(),
                menu.getModifiedAt()
        );
    }

}