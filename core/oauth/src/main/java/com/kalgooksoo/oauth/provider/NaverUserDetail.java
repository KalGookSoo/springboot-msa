package com.kalgooksoo.oauth.provider;

import java.util.Map;
import java.util.Optional;

public class NaverUserDetail implements OAuth2UserDetail {

    private final Map<String, Object> attributes;

    public NaverUserDetail(Map<String, Object> attributes) {
        //noinspection unchecked
        this.attributes = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getProviderId() {
        return Optional.ofNullable(this.attributes.get("id"))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return Optional.ofNullable(this.attributes.get("email"))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getName() {
        return Optional.ofNullable(this.attributes.get("name"))
                .map(Object::toString)
                .orElse(null);
    }

}
