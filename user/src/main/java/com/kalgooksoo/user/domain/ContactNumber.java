package com.kalgooksoo.user.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
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

    public String getValue() {
        return first + "-" + middle + "-" + last;
    }

}