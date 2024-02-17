package com.kalgooksoo.user.search;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class UserSearch {

    @Parameter(description = "계정명(대체키)")
    @Schema(description = "계정명(대체키)")
    private String username;

    @Parameter(description = "계정명(또는 별칭)")
    @Schema(description = "계정명(또는 별칭)")
    private String name;

    @Parameter(description = "이메일 ID")
    @Schema(description = "이메일 ID")
    private String emailId;

    @Parameter(description = "연락처")
    @Schema(description = "연락처")
    private String contactNumber;

    @Parameter(description = "페이지네이션 오프셋", schema = @Schema(defaultValue = "0"))
    @Schema(description = "페이지네이션 오프셋", defaultValue = "0")
    private int offset = 0;

    @Parameter(description = "페이지네이션 제한", schema = @Schema(defaultValue = "10"))
    @Schema(description = "페이지네이션 제한", defaultValue = "10")
    private int limit = 10;

    @Parameter(description = "정렬할 필드", schema = @Schema(defaultValue = "createdAt"))
    @Schema(description = "정렬할 필드", defaultValue = "createdAt")
    private String sort = "createdAt";

    @Parameter(description = "정렬 방향", schema = @Schema(defaultValue = "desc"))
    @Schema(description = "정렬 방향", defaultValue = "desc")
    private String sortDirection = "desc";

}