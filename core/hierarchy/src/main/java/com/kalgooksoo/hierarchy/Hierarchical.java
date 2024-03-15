package com.kalgooksoo.hierarchy;

public interface Hierarchical<T, ID> {

    /**
     * 최상위 노드 여부를 반환합니다.
     *
     * @return 최상위 노드 여부
     */
    boolean isRoot();

    /**
     * 부모 노트 식별
     *
     * @param parentId 부모 노드 식별자
     */
    void moveTo(ID parentId);

}
