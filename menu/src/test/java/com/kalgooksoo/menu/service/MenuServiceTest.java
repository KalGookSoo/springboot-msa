package com.kalgooksoo.menu.service;

import com.kalgooksoo.menu.command.MenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.model.HierarchicalMenu;
import com.kalgooksoo.menu.repository.MenuRepository;
import com.kalgooksoo.principal.PrincipalProvider;
import com.kalgooksoo.principal.StubPrincipalProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 메뉴 서비스 테스트
 */
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class MenuServiceTest {

    @Autowired
    private MenuRepository menuRepository;

    private MenuService menuService;

    private final PrincipalProvider principalProvider = new StubPrincipalProvider();

    private final String createdBy = principalProvider.getUsername();

    @BeforeEach
    void setup() {
        menuService = new DefaultMenuService(menuRepository);
    }

    @Test
    @DisplayName("메뉴를 생성합니다.")
    void createTest() {
        // Given
        Menu menu = Menu.create("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, createdBy);

        // When
        Menu savedMenu = menuService.create(menu);

        // Then
        assertNotNull(savedMenu);
    }

    @Test
    @DisplayName("메뉴를 수정합니다.")
    void updateTest() {
        // Given
        Menu menu = Menu.create("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, createdBy);
        Menu savedMenu = menuService.create(menu);

        // When
        MenuCommand command = new MenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles");
        Menu updatedMenu = menuService.update(savedMenu.getId(), command);

        // Then
        assertNotNull(updatedMenu);
        assertEquals(savedMenu.getId(), updatedMenu.getId());
        assertEquals("오시는 길", updatedMenu.getName());
    }

    @Test
    @DisplayName("메뉴를 조회합니다.")
    void findByIdTest() {
        // Given
        Menu menu = Menu.create("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, createdBy);
        Menu savedMenu = menuService.create(menu);

        // When
        Optional<Menu> foundMenu = menuService.findById(savedMenu.getId());

        // Then
        assertTrue(foundMenu.isPresent());
        assertEquals(savedMenu.getId(), foundMenu.get().getId());
    }

    @Test
    @DisplayName("메뉴를 삭제합니다.")
    void deleteByIdTest() {
        // Given
        Menu menu = Menu.create("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, createdBy);
        Menu savedMenu = menuService.create(menu);

        // When
        menuService.deleteById(savedMenu.getId());

        // Then
        Optional<Menu> foundMenu = menuService.findById(savedMenu.getId());
        assertTrue(foundMenu.isEmpty());
    }

    @Test
    @DisplayName("모든 메뉴를 조회합니다.")
    void findAllTest() {
        // Given
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Menu parent = Menu.create("메뉴" + i, "http://www.kalgooksoo.com/categories/" + i + "/articles", null, createdBy);
            Menu savedParent = menuService.create(parent);
            Menu child = Menu.create("하위메뉴" + i, "http://www.kalgooksoo.com/categories/" + (i + 10) + "/articles", savedParent.getId(), createdBy);
            Menu savedChild = menuService.create(child);
            Menu grandChild = Menu.create("하위하위메뉴" + i, "http://www.kalgooksoo.com/categories/" + (i + 10) + "/articles", savedChild.getId(), createdBy);
            menuService.create(grandChild);
        });

        // When
        List<HierarchicalMenu> menus = menuService.findAll();

        // Then
        assertEquals(5, menus.size());
        menus.forEach(hierarchicalMenu -> {
            assertEquals(1, hierarchicalMenu.children().size());
            hierarchicalMenu.children().forEach(child -> {
                assertEquals(1, child.children().size());
            });
        });
    }

    @Test
    @DisplayName("자식 메뉴를 추가합니다.")
    void addChildTest() {
        // Given
        Menu parent = Menu.create("국민참여", "http://www.kalgooksoo.com/categories/3/articles", null, createdBy);
        Menu savedParent = menuService.create(parent);
        Menu child = Menu.create("고객광장", "http://www.kalgooksoo.com/categories/4/articles", savedParent.getId(), createdBy);

        // When
        menuService.create(child);

        // Then
        Optional<Menu> foundParent = menuService.findById(savedParent.getId());
        assertTrue(foundParent.isPresent());
    }

}