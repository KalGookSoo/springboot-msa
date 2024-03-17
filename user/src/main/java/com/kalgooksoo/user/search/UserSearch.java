package com.kalgooksoo.user.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 계정 검색 조건
 */
@Getter
@Setter
@Schema(description = "계정 검색 조건")
public class UserSearch extends PageVO {

    @Parameter(description = "계정명(대체키)")
    @Schema(description = "계정명(대체키)", example = "testuser1")
    private String username;

    @Parameter(description = "계정명(또는 별칭)")
    @Schema(description = "계정명(또는 별칭)", example = "홍길동")
    private String name;

    @Parameter(description = "이메일 ID")
    @Schema(description = "이메일 ID", example = "testuser")
    private String emailId;

    @Parameter(description = "연락처")
    @Schema(description = "연락처", example = "010-1234-5678")
    private String contactNumber;

    @JsonIgnore
    public boolean isEmptyUsername() {
        return username == null || username.isEmpty();
    }

    @JsonIgnore
    public boolean isEmptyName() {
        return name == null || name.isEmpty();
    }

    @JsonIgnore
    public boolean isEmptyEmailId() {
        return emailId == null || emailId.isEmpty();
    }

    @JsonIgnore
    public boolean isEmptyContactNumber() {
        return contactNumber == null || contactNumber.isEmpty();
    }

}