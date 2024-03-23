package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Category;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category save(@Nonnull Category category);

    List<Category> findAll();

    Optional<Category> findById(@Nonnull String id);

    void deleteById(@Nonnull String id);

}