package com.kalgooksoo.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

/**
 * 댓글
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_comment")
@DynamicInsert
public class Comment {

    /**
     * 식별자
     */
    @Id
    private String id;

    /**
     * 게시글 식별자
     */
    private String articleId;

    /**
     * 본문
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 생성자
     */
    private String createdBy;

    /**
     * 생성 일시
     */
    private LocalDateTime createdAt;

    /**
     * 수정 일시
     */
    private LocalDateTime modifiedAt;

    /**
     * 좋아요
     */
    private int likes;

    /**
     * 싫어요
     */
    private int dislikes;

    /**
     * 댓글을 생성합니다.
     *
     * @param articleId 게시글 식별자
     * @param content   본문
     * @param createdBy 생성자
     * @return 댓글
     */
    public static Comment create(String articleId, String content, String createdBy) {
        Assert.notNull(createdBy, "생성자는 필수입니다.");
        Comment comment = new Comment();
        comment.id = UUID.randomUUID().toString();
        comment.articleId = articleId;
        comment.content = content;
        comment.createdBy = createdBy;
        comment.createdAt = LocalDateTime.now();
        return comment;
    }

    /**
     * 댓글을 수정합니다.
     *
     * @param content 본문
     */
    public void update(String content) {
        this.content = content;
        this.modifiedAt = LocalDateTime.now();
    }

    /**
     * 좋아요를 증가시킵니다.
     */
    public void increaseLikes() {
        this.likes++;
    }

    /**
     * 좋아요를 감소시킵니다.
     */
    public void decreaseLikes() {
        if (likes == 0) {
            throw new IllegalStateException("좋아요가 0입니다");
        }
        this.likes--;
    }

    /**
     * 싫어요를 증가시킵니다.
     */
    public void increaseDislikes() {
        this.dislikes++;
    }

    /**
     * 싫어요를 감소시킵니다.
     */
    public void decreaseDislikes() {
        if (dislikes == 0) {
            throw new IllegalStateException("싫어요가 0입니다");
        }
        this.dislikes--;
    }

}