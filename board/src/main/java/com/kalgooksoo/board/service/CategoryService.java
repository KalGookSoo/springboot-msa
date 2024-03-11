package com.kalgooksoo.board.service;

import com.kalgooksoo.board.domain.Category;
import com.kalgooksoo.board.model.CreateCategoryCommand;
import com.kalgooksoo.board.model.HierarchicalCategory;
import com.kalgooksoo.board.model.MoveCategoryCommand;
import com.kalgooksoo.board.model.UpdateCategoryCommand;

import java.util.List;
import java.util.Optional;

/**
 * 카테고리 서비스
 */
public interface CategoryService {

    Category create(CreateCategoryCommand command);

    List<HierarchicalCategory> findAll();

    Optional<Category> findById(String id);

    Category update(String id, UpdateCategoryCommand command);

    void delete(String id);

    Category move(String id, MoveCategoryCommand command);

}