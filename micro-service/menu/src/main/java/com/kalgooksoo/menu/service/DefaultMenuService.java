package com.kalgooksoo.menu.service;

import com.kalgooksoo.core.principal.PrincipalProvider;
import com.kalgooksoo.menu.command.CreateMenuCommand;
import com.kalgooksoo.menu.command.MoveMenuCommand;
import com.kalgooksoo.menu.command.UpdateMenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.repository.MenuRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kalgooksoo.menu.repository.MenuRepository.NOT_FOUND_MESSAGE;

/**
 * 메뉴 서비스
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultMenuService implements MenuService {

    private final MenuRepository menuRepository;

    private final PrincipalProvider principalProvider;

    @Override
    public Menu create(@Nonnull CreateMenuCommand command) {
        String createdBy = principalProvider.getUsername();
        Menu menu = Menu.create(command.name(), command.url(), command.parentId(), createdBy);
        return menuRepository.save(menu);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Menu> findAll() {
        List<Menu> menus = menuRepository.findAll();

        Map<String, List<Menu>> menuMap = menus.stream()
                .collect(Collectors.groupingBy(menu -> Optional.ofNullable(menu.getParentId()).orElse("ROOT")));

        return menus.stream()
                .filter(Menu::isRoot)
                .map(menu -> menu.mapChildren(menu, menuMap))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Menu findById(@Nonnull String id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_MESSAGE));
    }

    @Override
    public Menu update(@Nonnull String id, @Nonnull UpdateMenuCommand command) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("메뉴가 존재하지 않습니다"));
        menu.update(command.name(), command.url());
        return menuRepository.save(menu);
    }

    @Override
    public void delete(@Nonnull String id) {
        menuRepository.deleteById(id);
    }

    @Override
    public Menu move(@Nonnull String id, @Nonnull MoveMenuCommand command) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_MESSAGE));
        menu.moveTo(command.parentId());
        return menuRepository.save(menu);
    }

}