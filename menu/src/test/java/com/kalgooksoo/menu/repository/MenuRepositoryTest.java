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

    private Menu testMenu;

    @BeforeEach
    void setup() {
        menuRepository = new MenuJpaRepository(entityManager.getEntityManager());
    }

    @Test
    @DisplayName("메뉴를 생성합니다.")
    void saveTest() {
        // Given
        Menu menu = Menu.createRoot("공지사항", "http://www.kalgooksoo.com/boards/1/articles", createdBy);

        // When
        Menu savedMenu = menuRepository.save(menu);

        // Then
        assertNotNull(savedMenu);
    }

}