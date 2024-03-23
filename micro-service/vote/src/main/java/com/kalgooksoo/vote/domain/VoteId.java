package com.kalgooksoo.vote.domain;

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
public class VoteId implements Serializable {

    /**
     * 참조 식별자
     */
    private String referenceId;

    /**
     * 투표자
     */
    private String voter;

    public VoteId(String referenceId, String voter) {
        Assert.notNull(referenceId, "참조 식별자는 NULL이 될 수 없습니다");
        Assert.notNull(voter, "투표자는 NULL이 될 수 없습니다");
        this.referenceId = referenceId;
        this.voter = voter;
    }

}
