package com.kalgooksoo.category.repository;

import com.kalgooksoo.category.domain.Category;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CategoryMemoryRepository implements CategoryRepository {

    private final List<Category> categories = new ArrayList<>();

    @Override
    public Category save(@Nonnull Category category) {
        if (category.getId() == null) {
            categories.add(category);
        } else {
            categories.stream()
                    .filter(c -> c.getId().equals(category.getId()))
                    .findFirst()
                    .map(c -> category)
                    .orElseGet(() -> {
                        categories.add(category);
                        return category;
                    });
        }
        return category;
    }

    @Override
    public List<Category> findAll() {
        return categories;
    }

    @Override
    public Optional<Category> findById(@Nonnull String id) {
        return categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(@Nonnull String id) {
        categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst()
                .map(categories::remove)
                .orElseThrow(() -> new NoSuchElementException("카테고리를 찾을 수 없습니다."));
    }

}
