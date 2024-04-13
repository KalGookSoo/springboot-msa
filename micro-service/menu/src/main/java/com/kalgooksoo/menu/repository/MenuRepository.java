package com.kalgooksoo.menu.repository;

import com.kalgooksoo.menu.domain.Menu;
import jakarta.annotation.Nonnull;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 메뉴 저장소
 */
public interface MenuRepository extends Repository<Menu, Long> {

    String NOT_FOUND_MESSAGE = "메뉴가 존재하지 않습니다";

    Menu save(@Nonnull Menu menu);

    List<Menu> findAll();

    Optional<Menu> findById(@Nonnull String id);

    Menu getReferenceById(@Nonnull String id);

    void deleteById(@Nonnull String id);

}