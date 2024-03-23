package com.kalgooksoo.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactNumber {

    private String first;

    private String middle;

    private String last;

    protected ContactNumber() {
    }

    public ContactNumber(String first, String middle, String last) {
        this.first = first;
        this.middle = middle;
        this.last = last;
    }

    @JsonIgnore
    public String getValue() {
        return first + "-" + middle + "-" + last;
    }

}