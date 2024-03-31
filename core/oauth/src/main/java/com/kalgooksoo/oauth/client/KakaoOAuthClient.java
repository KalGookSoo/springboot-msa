package com.kalgooksoo.oauth.client;

import com.kalgooksoo.oauth.provider.KakaoUserDetail;
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
 * Kakao OAuth Client
 * {@link <a href="https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api">로그인 플로 직접 빌드</a>}
 */
public class KakaoOAuthClient implements OAuthClient {

    private static final String AUTHORIZE_PATH = "https://kauth.kakao.com/oauth/authorize";

    private static final String TOKEN_PATH = "https://kauth.kakao.com/oauth/token";

    private static final String USER_ME_PATH = "https://kapi.kakao.com/v2/user/me";

    private final RestTemplate restTemplate;

    private final String clientId;

    private final String clientSecret;

    private final String redirectUri;

    public KakaoOAuthClient(RestTemplate restTemplate, String clientId, String clientSecret, String redirectUri) {
        this.restTemplate = restTemplate;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    /**
     * {@link <a href="https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request">인증 코드 요청</a>}
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
                .build()
                .toUriString();
    }

    /**
     * {@link <a href="https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request">AccessToken 요청</a>}
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
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .build()
                .toUriString();

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(uriString, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {});

        verify(responseEntity);
        assert responseEntity.getBody() != null;

        return responseEntity.getBody().get("access_token").toString();
    }

    /**
     * {@link <a href="https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request">사용자 정보 요청</a>}
     *
     * @param accessToken AccessToken
     * @return 사용자 정보
     */
    @Override
    public OAuth2UserDetail authenticate(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<KakaoUserDetail> responseEntity = restTemplate.exchange(USER_ME_PATH, HttpMethod.GET, httpEntity, KakaoUserDetail.class);

        verify(responseEntity);

        return responseEntity.getBody();
    }

}
