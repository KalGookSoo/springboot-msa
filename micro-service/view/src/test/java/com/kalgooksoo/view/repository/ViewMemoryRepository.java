package com.kalgooksoo.view.repository;

import com.kalgooksoo.view.domain.View;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewMemoryRepository implements ViewRepository {

    private final List<View> views = new ArrayList<>();

    @Override
    public View save(@Nonnull View view) {
        if (view.getId() == null) {
            views.add(view);
        } else {
            views.stream()
                    .filter(v -> v.getId().equals(view.getId()))
                    .findFirst()
                    .map(v -> view)
                    .orElseGet(() -> {
                        views.add(view);
                        return view;
                    });
        }
        return view;
    }

    @Override
    public List<View> findAllByReferenceId(@Nonnull String referenceId) {
        return views.stream()
                .filter(view -> view.getId().getReferenceId().equals(referenceId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByReferenceId(@Nonnull String referenceId) {
        views.removeIf(view -> view.getId().getReferenceId().equals(referenceId));
    }

}
