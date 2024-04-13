package com.kalgooksoo.menu.repository;

import com.kalgooksoo.menu.domain.Menu;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class MenuMemoryRepository implements MenuRepository {

    private final List<Menu> menus = new ArrayList<>();

    @Override
    public Menu save(@Nonnull Menu menu) {
        if (menu.getId() == null) {
            menus.add(menu);
        } else {
            menus.stream()
                    .filter(m -> m.getId().equals(menu.getId()))
                    .findFirst()
                    .map(m -> menu)
                    .orElseGet(() -> {
                        menus.add(menu);
                        return menu;
                    });
        }
        return menu;
    }

    @Override
    public List<Menu> findAll() {
        return menus;
    }

    @Override
    public Optional<Menu> findById(@Nonnull String id) {
        return menus.stream()
                .filter(menu -> menu.getId().equals(id))
                .findFirst();
    }

    @Override
    public Menu getReferenceById(@Nonnull String id) {
        return menus.stream()
                .filter(menu -> menu.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_MESSAGE));
    }

    @Override
    public void deleteById(@Nonnull String id) {
        menus.stream()
                .filter(menu -> menu.getId().equals(id))
                .findFirst()
                .map(menus::remove)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_MESSAGE));
    }

}
