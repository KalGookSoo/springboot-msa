package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.Category;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CategoryMemoryRepository implements CategoryRepository {

    private final List<Category> categories = new ArrayList<>();

    @Override
    public Category save(Category category) {
        Assert.notNull(category, "카테고리는 NULL이 될 수 없습니다");
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
    public Optional<Category> findById(String id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        return categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "식별자는 NULL이 될 수 없습니다");
        categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst()
                .map(categories::remove)
                .orElseThrow(() -> new NoSuchElementException("카테고리를 찾을 수 없습니다."));
    }

}
