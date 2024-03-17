package com.kalgooksoo.board.search;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public abstract class PageVO {

    @Parameter(description = "페이지 인덱스")
    @Schema(description = "페이지 인덱스", defaultValue = "0", implementation = Integer.class, example = "0")
    private int page = 0;

    @Parameter(description = "페이지 당 출력할 레코드 수")
    @Schema(description = "페이지 당 출력할 레코드 수", defaultValue = "10", implementation = Integer.class, example = "10")
    private int size = 10;

    @Parameter(description = "정렬할 필드")
    @Schema(description = "정렬할 필드", defaultValue = "createdAt", implementation = String.class, example = "createdAt|updatedAt|id")
    private String sort = "createdAt";

    @Parameter(description = "정렬 방향")
    @Schema(description = "정렬 방향", defaultValue = "desc", implementation = String.class, example = "asc|desc")
    private String sortDirection = "desc";

    public Pageable pageable() {
        return PageRequest.of(page, size, sort());
    }

    public Sort sort() {
        return Sort.by(Sort.Direction.fromString(sortDirection), sort);
    }

}
