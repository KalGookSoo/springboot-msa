package com.kalgooksoo.board.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class ViewId implements Serializable {

    /**
     * 참조 식별자
     */
    private String referenceId;

    /**
     * 조회자
     */
    private String viewer;

    public ViewId(String referenceId, String viewer) {
        Assert.notNull(referenceId, "참조 식별자는 NULL이 될 수 없습니다");
        Assert.notNull(viewer, "조회자는 NULL이 될 수 없습니다");
        this.referenceId = referenceId;
        this.viewer = viewer;
    }

}
