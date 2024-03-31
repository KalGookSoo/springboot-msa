package com.kalgooksoo.oauth;

import com.kalgooksoo.oauth.provider.KakaoUserDetail;
import com.kalgooksoo.oauth.provider.OAuth2UserDetail;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class KakaoOAuthClient implements OAuthClient {

    private static final String AUTHORIZE_PATH = "https://kauth.kakao.com/oauth/authorize";

    private static final String TOKEN_PATH = "https://kauth.kakao.com/oauth/token";

    private static final String USER_ME_PATH = "https://kapi.kakao.com/v2/user/me";

    private final RestTemplate restTemplate;

    private final String kakaoClientId;

    private final String kakaoRedirectUri;

    public KakaoOAuthClient(RestTemplate restTemplate, String kakaoClientId, String kakaoRedirectUri) {
        this.restTemplate = restTemplate;
        this.kakaoClientId = kakaoClientId;
        this.kakaoRedirectUri = kakaoRedirectUri;
    }

    @Override
    public String getRedirectUri() {
        //noinspection VulnerableCodeUsages
        return UriComponentsBuilder
                .fromHttpUrl(AUTHORIZE_PATH)
                .queryParam("client_id", kakaoClientId)
                .queryParam("redirect_uri", kakaoRedirectUri)
                .queryParam("response_type", "code")
                .build()
                .toUriString();
    }

    @Override
    public String getAccessToken(String code) {
        //noinspection VulnerableCodeUsages
        String uriString = UriComponentsBuilder
                .fromHttpUrl(TOKEN_PATH)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", kakaoClientId)
                .queryParam("redirect_uri", kakaoRedirectUri)
                .queryParam("code", code)
                .build()
                .toUriString();

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate
                .exchange(uriString, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        verify(responseEntity);

        //noinspection DataFlowIssue
        return responseEntity.getBody()
                .get("access_token")
                .toString();
    }

    @Override
    public OAuth2UserDetail authenticate(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        ResponseEntity<KakaoUserDetail> responseEntity = restTemplate.exchange(USER_ME_PATH, HttpMethod.GET, new HttpEntity<>(httpHeaders), KakaoUserDetail.class);

        verify(responseEntity);

        return responseEntity.getBody();
    }

    private void verify(ResponseEntity<?> responseEntity) {
        Assert.isTrue(responseEntity.getStatusCode().is2xxSuccessful(), "카카오 서버 응답 오류");
        Assert.state(responseEntity.getStatusCode().is5xxServerError(), "카카오 서버 오류");
        Assert.state(responseEntity.getBody() != null, "카카오 서버 응답 오류");
    }

}
