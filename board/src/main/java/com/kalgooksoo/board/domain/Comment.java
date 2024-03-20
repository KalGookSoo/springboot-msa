package com.kalgooksoo.board.domain;

import com.kalgooksoo.core.hierarchy.Hierarchical;
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
public class Comment extends Hierarchical<Comment, String> {

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
     * 부모 댓글 식별자
     */
    private String parentId;

    /**
     * 본문
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 작성자
     */
    private String author;

    /**
     * 생성 일시
     */
    private LocalDateTime createdAt;

    /**
     * 수정 일시
     */
    private LocalDateTime modifiedAt;

    /**
     * 댓글을 생성합니다.
     *
     * @param articleId 게시글 식별자
     * @param parentId  부모 댓글 식별자
     * @param content   본문
     * @param author    작성자
     * @return 댓글
     */
    public static Comment create(String articleId, String parentId, String content, String author) {
        Assert.notNull(author, "작성자는 필수입니다.");
        Comment comment = new Comment();
        comment.id = UUID.randomUUID().toString();
        comment.articleId = articleId;
        comment.parentId = parentId;
        comment.content = content;
        comment.author = author;
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

    @Override
    public boolean isRoot() {
        return parentId == null;
    }

    @Override
    public void moveTo(String parentId) {
        this.parentId = parentId;
        this.modifiedAt = LocalDateTime.now();
    }

}