package com.kalgooksoo.menu.repository;

import com.kalgooksoo.menu.domain.Menu;

import java.util.List;
import java.util.Optional;

/**
 * 메뉴 저장소
 */
public interface MenuRepository {

    Menu save(Menu menu);

    Optional<Menu> findById(String id);

    List<Menu> findAll();

    void deleteById(String id);

}