package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);

    List<Category> findAll();

    Optional<Category> findById(String id);

    void deleteById(String id);

}