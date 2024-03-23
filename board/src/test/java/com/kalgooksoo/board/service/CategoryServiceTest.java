package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateCategoryCommand;
import com.kalgooksoo.board.command.MoveCategoryCommand;
import com.kalgooksoo.board.command.UpdateCategoryCommand;
import com.kalgooksoo.board.domain.Category;
import com.kalgooksoo.board.domain.CategoryType;
import com.kalgooksoo.board.model.HierarchicalCategory;
import com.kalgooksoo.board.repository.CategoryMemoryRepository;
import com.kalgooksoo.board.repository.CategoryRepository;
import com.kalgooksoo.core.principal.PrincipalProvider;
import com.kalgooksoo.core.principal.StubPrincipalProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 메뉴 서비스 테스트
 */
class CategoryServiceTest {

    private CategoryService categoryService;

    @BeforeEach
    void setup() {
        CategoryRepository categoryRepository = new CategoryMemoryRepository();
        PrincipalProvider principalProvider = new StubPrincipalProvider();
        categoryService = new DefaultCategoryService(categoryRepository, principalProvider);
    }

    @Test
    @DisplayName("카테고리를 생성합니다. 성공 시 카테고리를 반환합니다.")
    void createTest() {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name());

        // When
        Category savedCategory = categoryService.create(createCategoryCommand);

        // Then
        assertNotNull(savedCategory);
    }

    @Test
    @DisplayName("모든 카테고리를 조회합니다. 성공 시 카테고리 목록을 반환합니다.")
    void findAllTest() {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name());
        categoryService.create(createCategoryCommand);

        // When
        List<HierarchicalCategory> categories = categoryService.findAll();

        // Then
        assertTrue(categories.stream().anyMatch(c -> c.name().equals("공지사항")));
    }

    @Test
    @DisplayName("모든 카테고리를 조회합니다. 실패 시 빈 목록을 반환합니다.")
    void findAllTestWithEmpty() {
        // When
        List<HierarchicalCategory> categories = categoryService.findAll();

        // Then
        assertTrue(categories.isEmpty());
    }

    @Test
    @DisplayName("카테고리를 조회합니다.")
    void findByIdTest() {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name());
        Category savedCategory = categoryService.create(createCategoryCommand);

        // When
        Category foundCategory = categoryService.findById(savedCategory.getId());

        // Then
        assertNotNull(foundCategory);
    }

    @Test
    @DisplayName("카테고리를 조회합니다. 존재하지 않는 카테고리에 대해 조회를 시도할 경우 NoSuchElementException을 던집니다.")
    void findByIdShouldThrowNoSuchElementException() {
        // Given
        String invalidId = UUID.randomUUID().toString();

        // When & Then
        assertThrows(NoSuchElementException.class, () -> categoryService.findById(invalidId));
    }

    @Test
    @DisplayName("카테고리를 수정합니다. 성공 시 수정된 카테고리를 반환합니다.")
    void updateTest() {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name());
        Category savedCategory = categoryService.create(createCategoryCommand);
        UpdateCategoryCommand updateCategoryCommand = new UpdateCategoryCommand("공지사항 수정", CategoryType.PUBLIC.name());

        // When
        Category updatedCategory = categoryService.update(savedCategory.getId(), updateCategoryCommand);

        // Then
        assertEquals("공지사항 수정", updatedCategory.getName());
    }

    @Test
    @DisplayName("카테고리를 수정합니다. 존재하지 않는 카테고리에 대해 수정을 시도할 경우 NoSuchElementException을 던집니다.")
    void updateShouldThrowNoSuchElementException() {
        // Given
        String invalidId = UUID.randomUUID().toString();
        UpdateCategoryCommand updateCategoryCommand = new UpdateCategoryCommand("공지사항 수정", CategoryType.PUBLIC.name());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> categoryService.update(invalidId, updateCategoryCommand));
    }

    @Test
    @DisplayName("카테고리를 삭제합니다. 성공 시 삭제된 카테고리를 조회할 경우 NoSuchElementException을 던집니다.")
    void deleteTest() {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name());
        Category savedCategory = categoryService.create(createCategoryCommand);

        // When
        categoryService.delete(savedCategory.getId());

        // Then
        assertThrows(NoSuchElementException.class, () -> categoryService.findById(savedCategory.getId()));
    }

    @Test
    @DisplayName("카테고리를 삭제합니다. 존재하지 않는 카테고리에 대해 삭제를 시도할 경우 NoSuchElementException을 던집니다.")
    void deleteShouldThrowNoSuchElementException() {
        // Given
        String invalidId = UUID.randomUUID().toString();

        // When & Then
        assertThrows(NoSuchElementException.class, () -> categoryService.delete(invalidId));
    }

    @Test
    @DisplayName("카테고리를 이동합니다. 성공 시 이동된 카테고리를 반환합니다.")
    void moveToTest() {
        // Given
        CreateCategoryCommand createCategoryCommand1 = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name());
        Category savedCategory1 = categoryService.create(createCategoryCommand1);
        CreateCategoryCommand createCategoryCommand2 = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name());
        Category savedCategory2 = categoryService.create(createCategoryCommand2);
        MoveCategoryCommand moveCategoryCommand = new MoveCategoryCommand(savedCategory1.getId());

        // When
        Category movedCategory = categoryService.move(savedCategory2.getId(), moveCategoryCommand);

        // Then
        assertEquals(movedCategory.getParentId(), savedCategory1.getId());
    }

    @Test
    @DisplayName("카테고리를 이동합니다. 존재하지 않는 카테고리에 대해 이동을 시도할 경우 NoSuchElementException을 던집니다.")
    void moveToShouldThrowNoSuchElementException() {
        // Given
        String invalidId = UUID.randomUUID().toString();
        MoveCategoryCommand moveCategoryCommand = new MoveCategoryCommand(UUID.randomUUID().toString());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> categoryService.move(invalidId, moveCategoryCommand));
    }

}