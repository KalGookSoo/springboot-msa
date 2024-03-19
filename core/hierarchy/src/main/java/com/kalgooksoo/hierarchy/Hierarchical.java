package com.kalgooksoo.hierarchy;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 계층형 구조를 표현하는 추상 클래스입니다.
 *
 * @param <T>  자식 노드의 타입을 나타냅니다.
 * @param <ID> 식별자의 타입을 나타냅니다.
 */
@Getter
public abstract class Hierarchical<T extends Hierarchical<T, ID>, ID> {

    /**
     * 자식 노드들을 저장하는 리스트입니다.
     */
    protected List<T> children = new ArrayList<>();

    /**
     * 이 메소드는 현재 노드가 최상위 노드인지를 판단합니다.
     *
     * @return 최상위 노드인 경우 true를 반환합니다.
     */
    public abstract boolean isRoot();

    /**
     * 이 메소드는 현재 노드를 주어진 부모 노드 아래로 이동시킵니다.
     *
     * @param parentId 이 노드를 부모로 설정할 노드의 식별자입니다.
     */
    public abstract void moveTo(ID parentId);

    /**
     * 이 메소드는 현재 노드의 식별자를 반환합니다.
     *
     * @return 현재 노드의 식별자를 반환합니다.
     */
    public abstract ID getId();

    /**
     * 이 메소드는 현재 노드의 부모 노드의 식별자를 반환합니다.
     *
     * @return 부모 노드의 식별자를 반환합니다.
     */
    public abstract ID getParentId();

    /**
     * 주어진 부모 노드와 그룹화된 노드 맵을 사용하여 계층 구조를 매핑합니다.
     * 이 메소드는 재귀적으로 호출되어 전체 트리 구조를 생성합니다.
     *
     * @param parentNode           부모 노드입니다. 이 노드 아래에 자식 노드들이 매핑됩니다.
     * @param nodesGroupByParentId 부모 노드의 식별자를 키로, 해당 부모를 가진 자식 노드들의 리스트를 값으로 가지는 맵입니다.
     * @return 자식 노드들이 매핑된 부모 노드를 반환합니다. 이 노드는 입력으로 주어진 부모 노드와 동일하지만, 자식 노드들이 추가된 상태입니다.
     */
    public T mapChildren(T parentNode, Map<ID, List<T>> nodesGroupByParentId) {
        List<T> children = nodesGroupByParentId.getOrDefault(parentNode.getId(), new ArrayList<>());
        parentNode.children = children.stream().map(child -> mapChildren(child, nodesGroupByParentId)).toList();
        return parentNode;
    }

}