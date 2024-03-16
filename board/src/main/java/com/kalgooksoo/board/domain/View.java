package com.kalgooksoo.board.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

/**
 * 뷰
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_view")
@DynamicInsert
public class View {

    /**
     * 식별자
     */
    @EmbeddedId
    private ViewId id;

    /**
     * 조회 일시
     */
    private LocalDateTime viewedAt;

    public View(String referenceId, String viewer) {
        this.id = new ViewId(referenceId, viewer);
        this.viewedAt = LocalDateTime.now();
    }

}
