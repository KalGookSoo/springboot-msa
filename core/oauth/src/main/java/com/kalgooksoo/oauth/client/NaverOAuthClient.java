package com.kalgooksoo.oauth.client;

import com.kalgooksoo.oauth.provider.NaverUserDetail;
import com.kalgooksoo.oauth.provider.OAuth2UserDetail;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Naver OAuth Client
 * {@link <a href="https://developers.naver.com/docs/login/api/">로그인 플로 직접 빌드</a>}
 */
public class NaverOAuthClient implements OAuthClient {

    private static final String AUTHORIZE_PATH = "https://nid.naver.com/oauth2.0/authorize";

    private static final String TOKEN_PATH = "https://nid.naver.com/oauth2.0/token";

    private static final String USER_ME_PATH = "https://openapi.naver.com/v1/nid/me";

    private final RestTemplate restTemplate;

    private final String clientId;

    private final String clientSecret;

    private final String redirectUri;

    public NaverOAuthClient(RestTemplate restTemplate, String clientId, String clientSecret, String redirectUri) {
        this.restTemplate = restTemplate;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    /**
     * {@link <a href="https://developers.naver.com/docs/login/api/api.md#jsp">인증 코드 요청</a>}
     *
     * @return RedirectUri
     */
    @Override
    public String getRedirectUri() {
        //noinspection VulnerableCodeUsages
        return UriComponentsBuilder
                .fromHttpUrl(AUTHORIZE_PATH)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("state", "STATE")
                .build()
                .toUriString();
    }

    /**
     * {@link <a href="https://developers.naver.com/docs/login/api/api.md#2--callback-jsp">AccessToken 요청</a>}
     *
     * @param code 인증 코드
     * @return AccessToken
     */
    @Override
    public String getAccessToken(String code) {
        //noinspection VulnerableCodeUsages
        String uriString = UriComponentsBuilder
                .fromHttpUrl(TOKEN_PATH)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .queryParam("state", "STATE")
                .build()
                .toUriString();

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(uriString, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        verify(responseEntity);
        assert responseEntity.getBody() != null;

        return (String) responseEntity.getBody().get("access_token");
    }

    /**
     * {@link <a href="https://developers.naver.com/docs/login/profile/">사용자 정보 요청</a>}
     *
     * @param accessToken AccessToken
     * @return 사용자 정보
     */
    @Override
    public OAuth2UserDetail authenticate(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<NaverUserDetail> responseEntity = restTemplate.exchange(USER_ME_PATH, HttpMethod.GET, httpEntity, NaverUserDetail.class);

        verify(responseEntity);

        return responseEntity.getBody();
    }

}
