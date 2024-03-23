package com.kalgooksoo.category.service;

import com.kalgooksoo.category.command.CreateCategoryCommand;
import com.kalgooksoo.category.command.MoveCategoryCommand;
import com.kalgooksoo.category.command.UpdateCategoryCommand;
import com.kalgooksoo.category.domain.Category;
import com.kalgooksoo.category.repository.CategoryRepository;
import com.kalgooksoo.core.principal.PrincipalProvider;
import jakarta.annotation.Nonnull;
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
    public Category create(@Nonnull CreateCategoryCommand command) {
        Assert.notNull(command, "카테고리 생성 커맨드는 NULL이 될 수 없습니다.");
        String author = principalProvider.getUsername();
        Category category = Category.create(command.parentId(), command.name(), command.type(), author);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = categoryRepository.findAll();

        Map<String, List<Category>> categoryMap = categories.stream()
                .collect(Collectors.groupingBy(category -> Optional.ofNullable(category.getParentId()).orElse("ROOT")));

        return categories.stream()
                .filter(Category::isRoot)
                .map(category -> category.mapChildren(category, categoryMap))
                .toList();
    }

    @Override
    public Category findById(@Nonnull String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("카테고리가 존재하지 않습니다."));
    }

    @Override
    public Category update(@Nonnull String id, @Nonnull UpdateCategoryCommand command) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("카테고리가 존재하지 않습니다."));
        category.update(command.name(), command.type());
        return categoryRepository.save(category);
    }

    @Override
    public void delete(@Nonnull String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category move(@Nonnull String id, @Nonnull MoveCategoryCommand command) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("카테고리가 존재하지 않습니다."));
        category.moveTo(command.parentId());
        return categoryRepository.save(category);
    }

}