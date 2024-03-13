package com.kalgooksoo.menu.repository;

import com.kalgooksoo.menu.domain.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class MenuRepositoryTest {

    private MenuRepository menuRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup() {
        menuRepository = new MenuJpaRepository(entityManager.getEntityManager());
    }

    @Test
    @DisplayName("메뉴를 저장합니다. 성공 시 메뉴를 반환합니다.")
    void saveTest() {
        // Given
        Menu menu = Menu.create("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, "anonymous");

        // When
        Menu savedMenu = menuRepository.save(menu);

        // Then
        assertNotNull(savedMenu);
    }

    @Test
    @DisplayName("메뉴를 저장합니다. 실패 시 IllegalArgumentException을 던집니다.")
    void saveTestWithNull() {
        // Given
        Menu menu = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> menuRepository.save(menu));
    }

    @Test
    @DisplayName("모든 메뉴를 조회합니다. 성공 시 메뉴 목록을 반환합니다.")
    void findAllTest() {
        // Given
        Menu menu = Menu.create("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, "anonymous");
        Menu savedMenu = menuRepository.save(menu);

        // When
        List<Menu> menus = menuRepository.findAll();

        // Then
        assertTrue(menus.stream().anyMatch(m -> m.getId().equals(savedMenu.getId())));
    }

    @Test
    @DisplayName("모든 메뉴를 조회합니다. 실패 시 메뉴 빈 목록을 반환합니다.")
    void findAllTestWithEmpty() {
        // When
        List<Menu> menus = menuRepository.findAll();

        // Then
        assertTrue(menus.isEmpty());
    }

    @Test
    @DisplayName("메뉴를 조회합니다. 성공 시 메뉴를 반환합니다.")
    void findByIdTest() {
        // Given
        Menu menu = Menu.create("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, "anonymous");
        Menu savedMenu = menuRepository.save(menu);

        // When
        Menu foundMenu = menuRepository.findById(savedMenu.getId()).orElse(null);

        // Then
        assertNotNull(foundMenu);
        assertEquals(savedMenu.getId(), foundMenu.getId());
    }

    @Test
    @DisplayName("메뉴를 조회합니다. 실패 시 실패 시 빈 Optional을 반환합니다.")
    void findByIdTestWithEmpty() {
        // Given
        String id = UUID.randomUUID().toString();

        // when
        Optional<Menu> foundMenu = menuRepository.findById(id);

        // Then
        assertTrue(foundMenu.isEmpty());
    }

    @Test
    @DisplayName("메뉴를 수정합니다. 없는 메뉴를 수정 시 NoSuchElementException을 던집니다.")
    void updateTestWithNull() {
        // Given
        String id = UUID.randomUUID().toString();

        // When & Then
        assertThrows(NoSuchElementException.class, () -> menuRepository.findById(id).orElseThrow());
    }

    @Test
    @DisplayName("메뉴를 삭제합니다. 성공 시 삭제된 메뉴를 조회할 수 없습니다.")
    void deleteTest() {
        // Given
        Menu menu = Menu.create("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, "anonymous");
        Menu savedMenu = menuRepository.save(menu);

        // When
        menuRepository.deleteById(savedMenu.getId());

        // Then
        Optional<Menu> deletedMenu = menuRepository.findById(savedMenu.getId());
        assertTrue(deletedMenu.isEmpty());
    }

    @Test
    @DisplayName("메뉴를 삭제합니다. 실패 시 IllegalArgumentException을 던집니다.")
    void deleteTestWithEmpty() {
        // Given
        Menu menu = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> menuRepository.save(menu));
    }

    @Test
    @DisplayName("메뉴를 삭제합니다. 없는 메뉴를 삭제 시 NoSuchElementException을 던집니다.")
    void deleteTestWithNull() {
        // Given
        String id = UUID.randomUUID().toString();

        // When & Then
        assertThrows(NoSuchElementException.class, () -> menuRepository.deleteById(id));
    }

}