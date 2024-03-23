package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateCategoryCommand;
import com.kalgooksoo.board.command.MoveCategoryCommand;
import com.kalgooksoo.board.command.UpdateCategoryCommand;
import com.kalgooksoo.board.domain.Category;
import com.kalgooksoo.board.model.HierarchicalCategory;

import java.util.List;

/**
 * 카테고리 서비스
 */
public interface CategoryService {

    Category create(CreateCategoryCommand command);

    List<HierarchicalCategory> findAll();

    Category findById(String id);

    Category update(String id, UpdateCategoryCommand command);

    void delete(String id);

    Category move(String id, MoveCategoryCommand command);

}