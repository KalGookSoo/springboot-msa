package com.kalgooksoo.oauth.provider;


import java.util.Map;
import java.util.Optional;

public class GoogleUserDetail implements OAuth2UserDetail {

    private final Map<String, Object> attributes;

    public GoogleUserDetail(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return Optional.ofNullable(this.attributes.get("sub"))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getProvider() {
        return "google";
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
