package com.woowaSisters.woowaSisters.oauth.jwt.config;

import com.woowaSisters.woowaSisters.oauth.AuthService;
import com.woowaSisters.woowaSisters.oauth.jwt.JwtTokenProvider;
import com.woowaSisters.woowaSisters.oauth.model.PrincipalDetails;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException, java.io.IOException {
        PrincipalDetails oAuth2User = (PrincipalDetails) authentication.getPrincipal();
        String targetUrl;
        String accessToken =  jwtTokenProvider.createAccessToken(oAuth2User.getUser().getId());
        String refreshToken =  jwtTokenProvider.createRefreshToken(oAuth2User.getUser().getId());

        authService.registerRefreshToken(oAuth2User.getUser().getId(),refreshToken);

        targetUrl = UriComponentsBuilder.fromUriString("/auth/oauth2/success")
                .queryParam("accessToken",accessToken)
                .queryParam("refreshToken",refreshToken)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
