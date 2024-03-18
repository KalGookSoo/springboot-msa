package com.kalgooksoo.hierarchy;

public abstract class Hierarchical<T, ID> {

    /**
     * 최상위 노드 여부를 반환합니다.
     *
     * @return 최상위 노드 여부
     */
    protected abstract boolean isRoot();

    /**
     * 부모 노트 식별
     *
     * @param parentId 부모 노드 식별자
     */
    protected abstract void moveTo(ID parentId);

}
