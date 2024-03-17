package com.kalgooksoo.board.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시글 검색 조건
 */
@Getter
@Setter
@Schema(description = "게시글 검색 조건")
public class ArticleSearch extends PageVO {

    @Parameter(
            description = "카테고리 식별자",
            example = "698506bb-152f-462d-9d36-456c75a7848d",
            required = true,
            schema = @Schema(implementation = String.class, description = "카테고리 식별자", example = "698506bb-152f-462d-9d36-456c75a7848d")
    )
    private String categoryId;

    @Parameter(
            description = "제목",
            example = "제목",
            schema = @Schema(implementation = String.class, description = "제목", example = "제목")
    )
    private String title;

    @Parameter(
            description = "내용",
            example = "내용",
            schema = @Schema(implementation = String.class, description = "내용", example = "내용")
    )
    private String content;

    @Parameter(
            description = "작성자",
            example = "작성자",
            schema = @Schema(implementation = String.class, description = "작성자", example = "작성자")
    )
    private String author;

    @JsonIgnore
    public boolean isEmptyTitle() {
        return title != null && !title.isEmpty();
    }

    @JsonIgnore
    public boolean isEmptyContent() {
        return content != null && !content.isEmpty();
    }

    @JsonIgnore
    public boolean isEmptyAuthor() {
        return author != null && !author.isEmpty();
    }

}