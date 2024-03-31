package com.kalgooksoo.oauth;

import com.kalgooksoo.oauth.provider.OAuth2UserDetail;

public interface OAuthClient {

    String getRedirectUri();

    String getAccessToken(String code);

    OAuth2UserDetail authenticate(String accessToken);

}
