package com.kalgooksoo.menu.service;

import com.kalgooksoo.menu.command.CreateMenuCommand;
import com.kalgooksoo.menu.command.MoveMenuCommand;
import com.kalgooksoo.menu.command.UpdateMenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * 메뉴 서비스
 */
public interface MenuService {

    Menu create(@Nonnull CreateMenuCommand menu);

    List<Menu> findAll();

    Menu findById(@Nonnull String id);

    Menu update(@Nonnull String id, @Nonnull UpdateMenuCommand command);

    void delete(@Nonnull String id);

    Menu move(@Nonnull String id, @Nonnull MoveMenuCommand command);

}