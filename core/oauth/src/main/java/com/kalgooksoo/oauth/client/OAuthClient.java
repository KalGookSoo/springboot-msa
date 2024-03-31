package com.kalgooksoo.oauth.client;

import com.kalgooksoo.oauth.provider.OAuth2UserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

public interface OAuthClient {

    String getRedirectUri();

    String getAccessToken(String code);

    OAuth2UserDetail authenticate(String accessToken);

    default void verify(ResponseEntity<?> responseEntity) {
        Assert.isTrue(responseEntity.getStatusCode().is2xxSuccessful(), "OAuth 서버 응답 오류");
        Assert.state(responseEntity.getStatusCode().is5xxServerError(), "OAuth 서버 오류");
        Assert.state(responseEntity.getBody() != null, "OAuth 서버 응답 오류");
    }

}
