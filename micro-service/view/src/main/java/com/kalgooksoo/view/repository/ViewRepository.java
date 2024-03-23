package com.kalgooksoo.view.repository;

import com.kalgooksoo.view.domain.View;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * 뷰 저장소
 */
public interface ViewRepository {

    View save(@Nonnull View view);

    List<View> findAllByReferenceId(@Nonnull String referenceId);

    void deleteAllByReferenceId(@Nonnull String referenceId);

}
