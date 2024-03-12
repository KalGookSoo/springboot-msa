package com.kalgooksoo.menu.service;

import com.kalgooksoo.menu.command.CreateMenuCommand;
import com.kalgooksoo.menu.command.MoveMenuCommand;
import com.kalgooksoo.menu.command.UpdateMenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.model.HierarchicalMenu;

import java.util.List;
import java.util.Optional;

/**
 * 메뉴 서비스
 */
public interface MenuService {

    Menu create(CreateMenuCommand menu);

    Menu update(String id, UpdateMenuCommand command);

    Optional<Menu> findById(String id);

    List<HierarchicalMenu> findAll();

    void delete(String id);

    Menu move(String id, MoveMenuCommand command);

}