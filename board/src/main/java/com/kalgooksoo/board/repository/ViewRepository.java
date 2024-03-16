package com.kalgooksoo.board.repository;

import com.kalgooksoo.board.domain.View;

import java.util.List;

/**
 * 뷰 저장소
 */
public interface ViewRepository {

    View save(View view);

    List<View> findAllByReferenceId(String referenceId);

    void deleteAllByReferenceId(String referenceId);

}
