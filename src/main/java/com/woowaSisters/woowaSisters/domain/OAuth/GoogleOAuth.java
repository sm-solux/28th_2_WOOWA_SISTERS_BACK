package com.woowaSisters.woowaSisters.domain.OAuth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowaSisters.woowaSisters.domain.OAuth.GoogleOAuthTokenDto;
import com.woowaSisters.woowaSisters.domain.OAuth.GoogleUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GoogleOAuth {
    private final String googleLoginUrl = "https://accounts.google.com";
    private final String GOOGLE_TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";
    private final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${app.google.clientId}")
    private String googleClientId;

    @Value("${app.google.redirect}")
    private String googleRedirectUrl;

    @Value("${app.google.secret}")
    private String getGoogleClientSecret;

    public ResponseEntity<String> requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", googleClientId);
        params.put("client_secret", getGoogleClientSecret);
        params.put("redirect_url", googleRedirectUrl);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity=restTemplate.postForEntity(GOOGLE_TOKEN_REQUEST_URL, params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        }
        return null;
    }

    public GoogleOAuthTokenDto getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        System.out.println("repsonse.getBody() = " + response.getBody());
        GoogleOAuthTokenDto googleOAuthTokenDto = objectMapper.readValue(response.getBody(), GoogleOAuthTokenDto.class);
        return googleOAuthTokenDto;
    }

   public ResponseEntity<String> requestUserInfo(GoogleOAuthTokenDto oAuthToken) {
       HttpHeaders headers = new HttpHeaders();
       headers.add("Authorization", "Bearer" + oAuthToken.getAccess_token());

       HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
       ResponseEntity<String> response = restTemplate.exchange(GOOGLE_USERINFO_REQUEST_URL, HttpMethod.GET, request, String.class);
       System.out.println("response.getBody() = " + response.getBody());
       return response;
   }

   public GoogleUserInfoDto getUserInfo(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        GoogleUserInfoDto googleUserInfoDto = objectMapper.readValue(response.getBody(), GoogleUserInfoDto.class);
        return googleUserInfoDto;
   }

    public String getOauthRedirectUrl() {
        // Google OAuth 리다이렉션 URL 생성 로직
        return "https://accounts.google.com/o/oauth2/v2/auth?client_id=...";
    }


}
