package com.kalgooksoo.menu.service;

import com.kalgooksoo.menu.command.MenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.model.HierarchicalMenu;

import java.util.List;
import java.util.Optional;

/**
 * 메뉴 서비스
 */
public interface MenuService {

    Menu create(Menu menu);

    Menu update(String id, MenuCommand command);

    Optional<Menu> findById(String id);

    List<HierarchicalMenu> findAll();

    void deleteById(String id);

}