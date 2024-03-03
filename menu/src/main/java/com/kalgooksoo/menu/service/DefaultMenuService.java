package com.kalgooksoo.menu.service;

import com.kalgooksoo.menu.command.MenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.model.HierarchicalMenu;
import com.kalgooksoo.menu.model.MenuHierarchyFactory;
import com.kalgooksoo.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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
    public Menu create(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu update(String id, MenuCommand command) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("메뉴가 존재하지 않습니다"));
        menu.update(command.name(), command.url());
        return menuRepository.save(menu);
    }

    @Override
    public Optional<Menu> findById(String id) {
        return menuRepository.findById(id);
    }

    @Override
    public List<HierarchicalMenu> findAll() {
        List<Menu> menus = menuRepository.findAll();

        Map<String, List<Menu>> menuMap = menus.stream()
                .collect(Collectors.groupingBy(menu -> Optional.ofNullable(menu.getParentId()).orElse("ROOT")));

        return menus.stream()
                .filter(Menu::isRoot)
                .map(menu -> MenuHierarchyFactory.toHierarchical(menu, menuMap))
                .toList();
    }

    @Override
    public void deleteById(String id) {
        menuRepository.deleteById(id);
    }

}