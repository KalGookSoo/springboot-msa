package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateCategoryCommand;
import com.kalgooksoo.board.command.MoveCategoryCommand;
import com.kalgooksoo.board.command.UpdateCategoryCommand;
import com.kalgooksoo.board.domain.Category;
import com.kalgooksoo.board.model.HierarchicalCategory;
import com.kalgooksoo.board.model.HierarchicalCategoryFactory;
import com.kalgooksoo.board.repository.CategoryRepository;
import com.kalgooksoo.core.principal.PrincipalProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

    private final PrincipalProvider principalProvider;

    @Override
    public Category create(CreateCategoryCommand command) {
        Assert.notNull(command, "카테고리 생성 커맨드는 NULL이 될 수 없습니다.");
        String author = principalProvider.getUsername();
        Category category = Category.create(command.parentId(), command.name(), command.type(), author);
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
    public Category findById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("카테고리가 존재하지 않습니다."));
    }

    @Override
    public Category update(String id, UpdateCategoryCommand command) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("카테고리가 존재하지 않습니다."));
        category.update(command.name(), command.type());
        return categoryRepository.save(category);
    }

    @Override
    public void delete(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category move(String id, MoveCategoryCommand command) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("카테고리가 존재하지 않습니다."));
        category.moveTo(command.parentId());
        return categoryRepository.save(category);
    }

}