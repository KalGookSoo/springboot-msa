package com.kalgooksoo.domain;

import lombok.Getter;

/**
 * 카테고리 타입
 */
@Getter
public enum CategoryType {
    PUBLIC("공개"),
    PRIVATE("비공개");

    private final String alias;

    CategoryType(String alias) {
        this.alias = alias;
    }
}
