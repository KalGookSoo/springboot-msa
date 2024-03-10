package com.kalgooksoo.board.service;

import com.kalgooksoo.board.domain.Category;
import com.kalgooksoo.board.model.CreateCategoryCommand;
import com.kalgooksoo.board.model.HierarchicalCategory;
import com.kalgooksoo.board.model.HierarchicalCategoryFactory;
import com.kalgooksoo.board.model.UpdateCategoryCommand;
import com.kalgooksoo.board.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @see CategoryService
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(CreateCategoryCommand command) {
        Category category = Category.create(command);
        return categoryRepository.save(category);
    }

    @Override
    public List<HierarchicalCategory> findAll() {
        List<Category> categories = categoryRepository.findAll();

        Map<String, List<Category>> categoryMap = categories.stream()
                .collect(Collectors.groupingBy(category -> Optional.ofNullable(category.getParentId()).orElse("ROOT")));

        return categories.stream()
                .filter(Category::isRoot)
                .map(category -> HierarchicalCategoryFactory.toHierarchical(category, categoryMap))
                .toList();
    }

    @Override
    public Optional<Category> findById(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void update(String id, UpdateCategoryCommand command) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("카테고리가 존재하지 않습니다."));
        category.update(command);
        categoryRepository.save(category);
    }

    @Override
    public void delete(String id) {
        categoryRepository.deleteById(id);
    }

}