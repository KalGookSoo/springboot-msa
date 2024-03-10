package com.kalgooksoo.menu.repository;

import com.kalgooksoo.menu.domain.Menu;

import java.util.List;
import java.util.Optional;

/**
 * 메뉴 저장소
 */
public interface MenuRepository {

    Menu save(Menu menu);

    List<Menu> findAll();

    Optional<Menu> findById(String id);

    void deleteById(String id);

}