package com.tradeshift.thirdparty.samples.springboot.services.Impl;

import com.tradeshift.thirdparty.samples.springboot.services.TokenService;
import org.apache.commons.codec.binary.Base64;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class TokenServiceImpl implements TokenService {

    static Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    private final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private final String AUTHORIZATION = "Basic ";
    private final String CHAR_SET = "utf-8";
    private final String AUTHORIZATION_CODE_GRANT_TYPE = "authorization_code";

    @Value("${userAuthorizationUri}")
    private String AUTHORIZE_URL;

    @Value("${accessTokenUri}")
    private String accessTokenUri;

    @Value("${clientID}")
    private String clientID;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${redirectUri}")
    private String redirectUri;

    /**
     * @return URL for oauth2 authorization
     */
    @Override
    public String getAuthorizationCodeURL() {
        LOGGER.info("get authorization url");
        String authorizationCodeURL = AUTHORIZE_URL + "&client_id=" + clientID + "&redirect_uri=" + redirectUri + "&scope=openid";

        return authorizationCodeURL;
    }

    /**
     * receive oauth2 token from authentication server
     *
     * @param authorizationCode from authentication server
     * @return oauth2 token
     * @throws IOException
     */
    @Override
    public OAuth2AccessToken getAuthorizationToken(String authorizationCode) throws IOException {
        OAuthRequest oAuthRequest = new OAuthRequest(Verb.POST, accessTokenUri);
        oAuthRequest.addHeader("Content-Type", CONTENT_TYPE);
        oAuthRequest.addHeader("Authorization", AUTHORIZATION + Base64.encodeBase64String(new String(clientID + ":" + clientSecret).getBytes()));
        oAuthRequest.setCharset(CHAR_SET);
        oAuthRequest.addBodyParameter("grant_type", AUTHORIZATION_CODE_GRANT_TYPE);
        oAuthRequest.addBodyParameter("code", authorizationCode);

        LOGGER.info("send request for access token", TokenServiceImpl.class);
        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(oAuthRequest.send().getBody());

        if (accessToken != null) {
            LOGGER.info("successfully get authorization token ");
        } else {
            LOGGER.info("failed get token", TokenServiceImpl.class);
        }

        return accessToken;
    }
}
