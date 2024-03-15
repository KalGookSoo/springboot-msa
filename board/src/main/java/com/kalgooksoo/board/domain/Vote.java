package com.kalgooksoo.board.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

/**
 * 투표
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_vote")
@DynamicInsert
public class Vote {

    /**
     * 식별자
     */
    @EmbeddedId
    private VoteId id;

    /**
     * 타입
     */
    @Enumerated(EnumType.STRING)
    private VoteType type;

    /**
     * 투표 일시
     */
    private LocalDateTime votedAt;

    public Vote(String referenceId, String voter, VoteType type) {
        this.id = new VoteId(referenceId, voter);
        this.type = type;
        this.votedAt = LocalDateTime.now();
    }

}
