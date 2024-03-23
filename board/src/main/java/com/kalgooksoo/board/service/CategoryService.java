package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateCategoryCommand;
import com.kalgooksoo.board.command.MoveCategoryCommand;
import com.kalgooksoo.board.command.UpdateCategoryCommand;
import com.kalgooksoo.board.domain.Category;
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