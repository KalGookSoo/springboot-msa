package com.kalgooksoo.menu.service;

import com.kalgooksoo.menu.command.CreateMenuCommand;
import com.kalgooksoo.menu.command.MoveMenuCommand;
import com.kalgooksoo.menu.command.UpdateMenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.model.HierarchicalMenu;
import com.kalgooksoo.menu.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
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

    @BeforeEach
    void setup() {
        menuService = new DefaultMenuService(menuRepository);
    }

    @Test
    @DisplayName("메뉴를 생성합니다.")
    void createTest() {
        // Given
        CreateMenuCommand command = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, "anonymous");

        // When
        Menu savedMenu = menuService.create(command);

        // Then
        assertNotNull(savedMenu);
    }

    @Test
    @DisplayName("메뉴를 수정합니다.")
    void updateTest() {
        // Given
        CreateMenuCommand command = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, "anonymous");
        Menu savedMenu = menuService.create(command);

        // When
        UpdateMenuCommand updateMenuCommand = new UpdateMenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles");
        Menu updatedMenu = menuService.update(savedMenu.getId(), updateMenuCommand);

        // Then
        assertNotNull(updatedMenu);
        assertEquals(savedMenu.getId(), updatedMenu.getId());
        assertEquals("오시는 길", updatedMenu.getName());
    }

    @Test
    @DisplayName("메뉴를 수정합니다. 존재하지 않는 메뉴에 대해 수정을 시도할 경우 NoSuchElementException이 발생합니다.")
    void updateShouldThrowNoSuchElementException() {
        // Given
        String invalidId = "invalidId";
        UpdateMenuCommand command = new UpdateMenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles");

        // Then
        assertThrows(NoSuchElementException.class, () -> menuService.update(invalidId, command));
    }

    @Test
    @DisplayName("메뉴를 조회합니다.")
    void findByIdTest() {
        // Given
        CreateMenuCommand createMenuCommand = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, "anonymous");
        Menu savedMenu = menuService.create(createMenuCommand);

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
        CreateMenuCommand createMenuCommand = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, "anonymous");
        Menu savedMenu = menuService.create(createMenuCommand);

        // When
        menuService.delete(savedMenu.getId());

        // Then
        Optional<Menu> foundMenu = menuService.findById(savedMenu.getId());
        assertTrue(foundMenu.isEmpty());
    }

    @Test
    @DisplayName("메뉴를 삭제합니다. 존재하지 않는 메뉴에 대해 삭제를 시도할 경우 NoSuchElementException이 발생합니다.")
    void deleteByIdShouldThrowNoSuchElementException() {
        // Given
        String invalidId = "invalidId";

        // Then
        assertThrows(NoSuchElementException.class, () -> menuService.delete(invalidId));
    }

    @Test
    @DisplayName("모든 메뉴를 조회합니다.")
    void findAllTest() {
        // Given
        IntStream.rangeClosed(1, 5).forEach(i -> {
            CreateMenuCommand createMenuCommand1 = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, "anonymous");
            Menu savedParent = menuService.create(createMenuCommand1);
            CreateMenuCommand createMenuCommand2 = new CreateMenuCommand("하위메뉴" + i, "http://www.kalgooksoo.com/categories/" + (i + 10) + "/articles", savedParent.getId(), "anonymous");
            Menu savedChild = menuService.create(createMenuCommand2);
            CreateMenuCommand createMenuCommand3 = new CreateMenuCommand("하위하위메뉴" + i, "http://www.kalgooksoo.com/categories/" + (i + 10) + "/articles", savedChild.getId(), "anonymous");
            menuService.create(createMenuCommand3);
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
        CreateMenuCommand createMenuCommand1 = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, "anonymous");
        Menu savedParent = menuService.create(createMenuCommand1);
        CreateMenuCommand createMenuCommand2 = new CreateMenuCommand("고객광장", "http://www.kalgooksoo.com/categories/4/articles", savedParent.getId(), "anonymous");

        // When
        Menu savedChild = menuService.create(createMenuCommand2);

        // Then
        assertEquals(savedParent.getId(), savedChild.getParentId());
    }

    @Test
    @DisplayName("메뉴를 이동합니다. 성공 시 이동된 메뉴를 반환합니다.")
    void moveToTest() {
        // Given
        CreateMenuCommand createMenuCommand1 = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, "anonymous");
        Menu savedMenu1 = menuService.create(createMenuCommand1);
        CreateMenuCommand createMenuCommand2 = new CreateMenuCommand("고객광장", "http://www.kalgooksoo.com/categories/4/articles", savedMenu1.getId(), "anonymous");
        Menu savedMenu2 = menuService.create(createMenuCommand2);
        MoveMenuCommand moveMenuCommand = new MoveMenuCommand(savedMenu1.getId());

        // When
        Menu movedMenu = menuService.move(savedMenu2.getId(), moveMenuCommand);

        // Then
        assertEquals(movedMenu.getParentId(), savedMenu1.getId());
    }

    @Test
    @DisplayName("메뉴를 이동합니다. 존재하지 않는 메뉴에 대해 이동을 시도할 경우 NoSuchElementException이 발생합니다.")
    void moveToShouldThrowNoSuchElementException() {
        // Given
        String invalidId = UUID.randomUUID().toString();
        MoveMenuCommand moveMenuCommand = new MoveMenuCommand(UUID.randomUUID().toString());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> menuService.move(invalidId, moveMenuCommand));
    }

}