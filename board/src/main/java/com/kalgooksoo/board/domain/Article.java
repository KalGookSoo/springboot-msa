package com.kalgooksoo.board.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

/**
 * 게시글
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_article")
@DynamicInsert
public class Article {

    /**
     * 식별자
     */
    @Id
    private String id;

    /**
     * 카테고리 식별자
     */
    private String categoryId;

    /**
     * 제목
     */
    private String title;

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
     * 게시글을 생성합니다.
     *
     * @param title      제목
     * @param content    본문
     * @param categoryId 카테고리 식별자
     * @param createdBy  생성자
     * @return 게시글
     */
    public static Article create(String title, String content, String categoryId, String createdBy) {
        Article article = new Article();
        article.id = UUID.randomUUID().toString();
        article.title = title;
        article.content = content;
        article.categoryId = categoryId;
        article.createdBy = createdBy;
        return article;
    }

    /**
     * 게시글을 수정합니다.
     *
     * @param title      제목
     * @param content    본문
     * @param categoryId 카테고리 식별자
     */
    public void update(String title, String content, String categoryId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
    }

}