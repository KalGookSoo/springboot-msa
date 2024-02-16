package com.kalgooksoo.user.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class UserSearch {

    private String username;
    private String name;
    private String emailId;
    private String contactNumber;
    private int offset = 0;
    private int limit = 10;
    private String sort = "createdAt";
    private String sortDirection = "desc";

}
