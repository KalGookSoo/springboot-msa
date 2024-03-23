package com.kalgooksoo.user.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Email {

    private String id;

    private String domain;

    protected Email() {
    }

    public Email(String id, String domain) {
        this.id = id;
        this.domain = domain;
    }

    @JsonIgnore
    public String getValue() {
        return id + "@" + domain;
    }

}