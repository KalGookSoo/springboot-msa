package com.kalgooksoo.menu.repository;

import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.principal.PrincipalProvider;
import com.kalgooksoo.principal.StubPrincipalProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MenuRepositoryTest {

    private MenuRepository menuRepository;

    private final PrincipalProvider principalProvider = new StubPrincipalProvider();

    private final String createdBy = principalProvider.getUsername();

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup() {
        menuRepository = new MenuJpaRepository(entityManager.getEntityManager());
    }

    @Test
    @DisplayName("메뉴를 생성합니다.")
    void saveTest() {
        // Given
        Menu menu = Menu.create("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, createdBy);

        // When
        Menu savedMenu = menuRepository.save(menu);

        // Then
        assertNotNull(savedMenu);
    }

    @Test
    @DisplayName("메뉴를 조회합니다.")
    void findByIdTest() {
        // Given
        Menu menu = Menu.create("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, createdBy);
        Menu savedMenu = menuRepository.save(menu);

        // When
        Menu foundMenu = menuRepository.findById(savedMenu.getId()).orElse(null);

        // Then
        assertNotNull(foundMenu);
        assertEquals(savedMenu.getId(), foundMenu.getId());
    }

    @Test
    @DisplayName("메뉴를 수정합니다.")
    void updateTest() {
        // Given
        Menu menu = Menu.create("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, createdBy);
        Menu savedMenu = menuRepository.save(menu);

        // When
        savedMenu.update("오시는 길", "http://www.kalgooksoo.com/categories/2/articles");
        Menu updatedMenu = menuRepository.save(savedMenu);

        // Then
        assertNotNull(updatedMenu);
        assertEquals(savedMenu.getId(), updatedMenu.getId());
        assertEquals("오시는 길", updatedMenu.getName());
    }

    @Test
    @DisplayName("메뉴를 삭제합니다.")
    void deleteTest() {
        // Given
        Menu menu = Menu.create("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, createdBy);
        Menu savedMenu = menuRepository.save(menu);

        // When
        menuRepository.deleteById(savedMenu.getId());
        Menu deletedMenu = menuRepository.findById(savedMenu.getId()).orElse(null);

        // Then
        assertNull(deletedMenu);
    }

}