package com.kalgooksoo.menu.service;

import com.kalgooksoo.menu.command.CreateMenuCommand;
import com.kalgooksoo.menu.command.MoveMenuCommand;
import com.kalgooksoo.menu.command.UpdateMenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.model.HierarchicalMenu;
import com.kalgooksoo.menu.model.HierarchicalMenuFactory;
import com.kalgooksoo.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 메뉴 서비스
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultMenuService implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public Menu create(CreateMenuCommand command) {
        Menu menu = Menu.create(command.name(), command.url(), command.parentId(), command.createdBy());
        return menuRepository.save(menu);
    }

    @Override
    public Menu update(String id, UpdateMenuCommand command) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("메뉴가 존재하지 않습니다"));
        menu.update(command.name(), command.url());
        return menuRepository.save(menu);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Menu> findById(String id) {
        return menuRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<HierarchicalMenu> findAll() {
        List<Menu> menus = menuRepository.findAll();

        Map<String, List<Menu>> menuMap = menus.stream()
                .collect(Collectors.groupingBy(menu -> Optional.ofNullable(menu.getParentId()).orElse("ROOT")));

        return menus.stream()
                .filter(Menu::isRoot)
                .map(menu -> HierarchicalMenuFactory.toHierarchical(menu, menuMap))
                .toList();
    }

    @Override
    public void delete(String id) {
        menuRepository.deleteById(id);
    }

    @Override
    public Menu move(String id, MoveMenuCommand command) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("메뉴가 존재하지 않습니다"));
        menu.moveTo(command.parentId());
        return menuRepository.save(menu);
    }

}