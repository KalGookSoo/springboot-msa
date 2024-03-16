package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.View;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewMemoryRepository implements ViewRepository {

    private final List<View> views = new ArrayList<>();

    @Override
    public View save(View view) {
        Assert.notNull(view, "투표는 NULL이 될 수 없습니다");
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
    public List<View> findAllByReferenceId(String referenceId) {
        Assert.notNull(referenceId, "참조 식별자는 NULL이 될 수 없습니다");
        return views.stream()
                .filter(view -> view.getId().getReferenceId().equals(referenceId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByReferenceId(String referenceId) {
        Assert.notNull(referenceId, "참조 식별자는 NULL이 될 수 없습니다");
        views.removeIf(view -> view.getId().getReferenceId().equals(referenceId));
    }

}
