package com.kalgooksoo.menu.repository;

import com.kalgooksoo.menu.domain.Menu;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Optional;

/**
 * 메뉴 저장소
 */
public interface MenuRepository {

    Menu save(@Nonnull Menu menu);

    List<Menu> findAll();

    Optional<Menu> findById(@Nonnull String id);

    void deleteById(@Nonnull String id);

}