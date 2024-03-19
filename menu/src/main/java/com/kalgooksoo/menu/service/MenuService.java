package com.kalgooksoo.menu.service;

import com.kalgooksoo.menu.command.CreateMenuCommand;
import com.kalgooksoo.menu.command.MoveMenuCommand;
import com.kalgooksoo.menu.command.UpdateMenuCommand;
import com.kalgooksoo.menu.domain.Menu;

import java.util.List;

/**
 * 메뉴 서비스
 */
public interface MenuService {

    Menu create(CreateMenuCommand menu);

    List<Menu> findAll();

    Menu findById(String id);

    Menu update(String id, UpdateMenuCommand command);

    void delete(String id);

    Menu move(String id, MoveMenuCommand command);

}