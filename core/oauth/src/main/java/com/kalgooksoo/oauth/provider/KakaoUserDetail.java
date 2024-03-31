package com.kalgooksoo.oauth.provider;

import java.util.Map;
import java.util.Optional;

public class KakaoUserDetail implements OAuth2UserDetail {

    private final Map<String, Object> attributes;

    public KakaoUserDetail(Map<String, Object> attributes) {
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
        return "kakao";
    }

    @Override
    public String getEmail() {
        return Optional.ofNullable(this.attributes.get("kaccount_email"))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getName() {
        return Optional.ofNullable(this.attributes.get("properties.name"))
                .map(Object::toString)
                .orElse(null);
    }

}
