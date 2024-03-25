package com.kalgooksoo.menu.service;

import com.kalgooksoo.core.principal.PrincipalProvider;
import com.kalgooksoo.core.principal.StubPrincipalProvider;
import com.kalgooksoo.menu.command.CreateMenuCommand;
import com.kalgooksoo.menu.command.MoveMenuCommand;
import com.kalgooksoo.menu.command.UpdateMenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.repository.MenuMemoryRepository;
import com.kalgooksoo.menu.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 메뉴 서비스 테스트
 */
class MenuServiceTest {

    private MenuService menuService;

    @BeforeEach
    void setup() {
        MenuRepository menuRepository = new MenuMemoryRepository();
        PrincipalProvider principalProvider = new StubPrincipalProvider();
        menuService = new DefaultMenuService(menuRepository, principalProvider);
    }

    @Test
    @DisplayName("메뉴를 생성합니다. 성공 시 생성된 메뉴를 반환합니다.")
    void createShouldReturnMenu() {
        // Given
        CreateMenuCommand command = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null);

        // When
        Menu savedMenu = menuService.create(command);

        // Then
        assertNotNull(savedMenu);
    }

    @Test
    @DisplayName("모든 계층형 메뉴를 조회합니다.")
    void findAllShouldReturnMenus() {
        // Given
        IntStream.rangeClosed(1, 5).forEach(i -> {
            CreateMenuCommand createMenuCommand1 = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null);
            Menu savedParent = menuService.create(createMenuCommand1);
            CreateMenuCommand createMenuCommand2 = new CreateMenuCommand("하위메뉴" + i, "http://www.kalgooksoo.com/categories/" + (i + 10) + "/articles", savedParent.getId());
            Menu savedChild = menuService.create(createMenuCommand2);
            CreateMenuCommand createMenuCommand3 = new CreateMenuCommand("하위하위메뉴" + i, "http://www.kalgooksoo.com/categories/" + (i + 10) + "/articles", savedChild.getId());
            menuService.create(createMenuCommand3);
        });

        // When
        List<Menu> menus = menuService.findAll();

        // Then
        assertEquals(5, menus.size());
        menus.forEach(hierarchicalMenu -> {
            assertEquals(1, hierarchicalMenu.getChildren().size());
            hierarchicalMenu.getChildren().forEach(child -> {
                assertEquals(1, child.getChildren().size());
            });
        });
    }

    @Test
    @DisplayName("모든 계층형 메뉴를 조회합니다. 메뉴가 없을 경우 빈 리스트를 반환합니다.")
    void findAllShouldReturnEmptyList() {
        // When
        List<Menu> menus = menuService.findAll();

        // Then
        assertTrue(menus.isEmpty());
    }

    @Test
    @DisplayName("메뉴를 조회합니다. 성공 시 조회된 메뉴를 반환합니다.")
    void findByIdShouldReturnMenu() {
        // Given
        CreateMenuCommand createMenuCommand = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null);
        Menu savedMenu = menuService.create(createMenuCommand);

        // When
        Menu foundMenu = menuService.findById(savedMenu.getId());

        // Then
        assertNotNull(foundMenu);
    }

    @Test
    @DisplayName("메뉴를 조회합니다. 존재하지 않는 메뉴를 조회할 경우 NoSuchElementException을 던집니다.")
    void findByIdShouldThrowNoSuchElementException() {
        // Given
        String invalidId = UUID.randomUUID().toString();

        // When & Then
        assertThrows(NoSuchElementException.class, () -> menuService.findById(invalidId));
    }

    @Test
    @DisplayName("메뉴를 수정합니다. 성공 시 수정된 메뉴를 반환합니다.")
    void updateShouldReturnMenu() {
        // Given
        CreateMenuCommand command = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null);
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
    @DisplayName("메뉴를 삭제합니다. 성공 시 삭제된 메뉴를 조회할 경우 NoSuchElementException을 던집니다.")
    void deleteTest() {
        // Given
        CreateMenuCommand createMenuCommand = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null);
        Menu savedMenu = menuService.create(createMenuCommand);

        // When
        menuService.delete(savedMenu.getId());

        // Then
        assertThrows(NoSuchElementException.class, () -> menuService.findById(savedMenu.getId()));
    }

    @Test
    @DisplayName("메뉴를 삭제합니다. 존재하지 않는 메뉴에 대해 삭제를 시도할 경우 NoSuchElementException이 발생합니다.")
    void deleteShouldThrowNoSuchElementException() {
        // Given
        String invalidId = UUID.randomUUID().toString();

        // Then
        assertThrows(NoSuchElementException.class, () -> menuService.delete(invalidId));
    }

    @Test
    @DisplayName("자식 메뉴를 추가합니다. 성공 시 자식 메뉴의 부모 식별자와 부모 메뉴의 식별자가 일치합니다.")
    void addChildShouldEquals() {
        // Given
        CreateMenuCommand createMenuCommand1 = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null);
        Menu savedParent = menuService.create(createMenuCommand1);
        CreateMenuCommand createMenuCommand2 = new CreateMenuCommand("고객광장", "http://www.kalgooksoo.com/categories/4/articles", savedParent.getId());

        // When
        Menu savedChild = menuService.create(createMenuCommand2);

        // Then
        assertEquals(savedParent.getId(), savedChild.getParentId());
    }

    @Test
    @DisplayName("메뉴를 이동합니다. 성공 시 이동된 메뉴를 반환합니다.")
    void moveShouldReturnMenu() {
        // Given
        CreateMenuCommand createMenuCommand1 = new CreateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null);
        Menu savedMenu1 = menuService.create(createMenuCommand1);
        CreateMenuCommand createMenuCommand2 = new CreateMenuCommand("고객광장", "http://www.kalgooksoo.com/categories/4/articles", savedMenu1.getId());
        Menu savedMenu2 = menuService.create(createMenuCommand2);
        MoveMenuCommand moveMenuCommand = new MoveMenuCommand(savedMenu1.getId());

        // When
        Menu movedMenu = menuService.move(savedMenu2.getId(), moveMenuCommand);

        // Then
        assertEquals(movedMenu.getParentId(), savedMenu1.getId());
    }

    @Test
    @DisplayName("메뉴를 이동합니다. 존재하지 않는 메뉴에 대해 이동을 시도할 경우 NoSuchElementException이 발생합니다.")
    void moveShouldThrowNoSuchElementException() {
        // Given
        String invalidId = UUID.randomUUID().toString();
        MoveMenuCommand moveMenuCommand = new MoveMenuCommand(UUID.randomUUID().toString());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> menuService.move(invalidId, moveMenuCommand));
    }

}