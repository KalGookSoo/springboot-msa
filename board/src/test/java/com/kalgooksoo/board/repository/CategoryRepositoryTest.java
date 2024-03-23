package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Category;
import com.kalgooksoo.board.domain.CategoryType;
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
class CategoryRepositoryTest {

    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup() {
        categoryRepository = new CategoryJpaRepository(entityManager.getEntityManager());
    }

    @Test
    @DisplayName("카테고리를 저장합니다. 성공 시 카테고리를 반환합니다.")
    void saveShouldReturnCategory() {
        // Given
        Category category = Category.create(null, "공지사항", CategoryType.PUBLIC.name(), "admin");

        // When
        Category savedCategory = categoryRepository.save(category);

        // Then
        assertNotNull(savedCategory);
    }

    @Test
    @DisplayName("모든 카테고리를 조회합니다. 성공 시 카테고리 목록을 반환합니다.")
    void findAllShouldReturnCategories() {
        // Given
        Category category = Category.create(null, "공지사항", CategoryType.PUBLIC.name(), "admin");
        Category savedCategory = categoryRepository.save(category);

        // When
        List<Category> categories = categoryRepository.findAll();

        // Then
        assertNotNull(categories);
        assertTrue(categories.stream().anyMatch(c -> c.getId().equals(savedCategory.getId())));
    }

    @Test
    @DisplayName("모든 카테고리를 조회합니다. 실패 시 빈 목록을 반환합니다.")
    void findAllShouldReturnEmptyList() {
        // When
        List<Category> categories = categoryRepository.findAll();

        // Then
        assertNotNull(categories);
        assertTrue(categories.isEmpty());
    }

    @Test
    @DisplayName("카테고리를 조회합니다. 성공 시 카테고리를 반환합니다.")
    void findByIdShouldReturnCategory() {
        // Given
        Category category = Category.create(null, "공지사항", CategoryType.PUBLIC.name(), "admin");
        Category savedCategory = categoryRepository.save(category);

        // When
        Optional<Category> foundCategory = categoryRepository.findById(savedCategory.getId());

        // Then
        assertTrue(foundCategory.isPresent());
    }

    @Test
    @DisplayName("카테고리를 조회합니다. 실패 시 빈 Optional을 반환합니다.")
    void findByIdShouldReturnEmptyOptional() {
        // Given
        String id = UUID.randomUUID().toString();

        // When
        Optional<Category> foundCategory = categoryRepository.findById(id);

        // Then
        assertTrue(foundCategory.isEmpty());
    }

    @Test
    @DisplayName("카테고리를 삭제합니다. 성공 시 삭제된 카테고리를 조회할 수 없습니다.")
    void deleteTest() {
        // Given
        Category category = Category.create(null, "공지사항", CategoryType.PUBLIC.name(), "admin");
        Category savedCategory = categoryRepository.save(category);

        // When
        categoryRepository.deleteById(savedCategory.getId());

        // Then
        Optional<Category> deletedCategory = categoryRepository.findById(savedCategory.getId());
        assertTrue(deletedCategory.isEmpty());
    }

    @Test
    @DisplayName("카테고리를 삭제합니다. 없는 카테고리를 삭제 시 NoSuchElementException을 던집니다.")
    void deleteShouldThrowNoSuchElementException() {
        // Given
        String id = UUID.randomUUID().toString();

        // When & Then
        assertThrows(NoSuchElementException.class, () -> categoryRepository.deleteById(id));
    }

}