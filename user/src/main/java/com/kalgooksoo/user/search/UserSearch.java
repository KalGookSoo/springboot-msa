package com.kalgooksoo.user.search;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 계정 검색 조건
 */
@Getter
@Setter
public class UserSearch extends PageVO {

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

    public boolean isEmptyUsername() {
        return username == null || username.isEmpty();
    }

    public boolean isEmptyName() {
        return name == null || name.isEmpty();
    }

    public boolean isEmptyEmailId() {
        return emailId == null || emailId.isEmpty();
    }

    public boolean isEmptyContactNumber() {
        return contactNumber == null || contactNumber.isEmpty();
    }

}