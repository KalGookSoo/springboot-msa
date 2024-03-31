package com.kalgooksoo.oauth.provider;


import java.util.Map;
import java.util.Optional;

public class FacebookUserDetail implements OAuth2UserDetail {

    private final Map<String, Object> attributes;

    public FacebookUserDetail(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return Optional.ofNullable(this.attributes.get("id"))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getProvider() {
        return "facebook";
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
