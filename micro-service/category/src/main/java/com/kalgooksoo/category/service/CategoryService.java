package com.kalgooksoo.category.service;

import com.kalgooksoo.category.command.CreateCategoryCommand;
import com.kalgooksoo.category.command.MoveCategoryCommand;
import com.kalgooksoo.category.command.UpdateCategoryCommand;
import com.kalgooksoo.category.domain.Category;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * 카테고리 서비스
 */
public interface CategoryService {

    Category create(@Nonnull CreateCategoryCommand command);

    List<Category> findAll();

    Category findById(@Nonnull String id);

    Category update(@Nonnull String id, @Nonnull UpdateCategoryCommand command);

    void delete(@Nonnull String id);

    Category move(@Nonnull String id, @Nonnull MoveCategoryCommand command);

}