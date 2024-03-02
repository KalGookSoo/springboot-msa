package com.kalgooksoo.user.domain;


import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Email {

    private String id;

    private String domain;

    protected Email() {
    }

    public Email(String id, String domain) {
        this.id = id;
        this.domain = domain;
    }

    public String getValue() {
        return id + "@" + domain;
    }

}