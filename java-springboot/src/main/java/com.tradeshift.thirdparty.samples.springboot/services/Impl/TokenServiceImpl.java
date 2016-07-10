package com.tradeshift.thirdparty.samples.springboot.services.Impl;

import com.tradeshift.thirdparty.samples.springboot.config.PropertySources;
import com.tradeshift.thirdparty.samples.springboot.services.TokenService;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TokenServiceImpl implements TokenService {

    static Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    private final String HEADER_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private final String HEADER_AUTHORIZATION_TYPE = "Basic ";
    private final String HEADER_CHAR_SET_TYPE = "utf-8";
    private final String AUTHORIZATION_CODE_GRANT_TYPE = "authorization_code";
    private final String AUTHORIZE_URL;
    private final String ACCESS_TOKEN_URI;

    private OAuth2AccessToken accessToken;
    private PropertySources propertySources;

    /**
     * Inject PropertySources bean by constructor, init AUTHORIZE_URL, ACCESS_TOKEN_URI
     *
     *
     * @param propertySources
     */
    @Autowired
    public TokenServiceImpl(@Qualifier("propertySources") PropertySources propertySources) {
        super();
        this.propertySources = propertySources;
        AUTHORIZE_URL = propertySources.getTradeShiftAPIDomainName() + "/tradeshift/auth/login?response_type=code";
        ACCESS_TOKEN_URI = propertySources.getTradeShiftAPIDomainName() + "/tradeshift/auth/token";
    }

    /**
     * Get authorization server url for get authorization code
     *
     *
     * @return URL for oauth2 authorization
     */
    @Override
    public String getAuthorizationCodeURL() {
        LOGGER.info("get authorization url", TokenServiceImpl.class);

        String authorizationCodeURL = AUTHORIZE_URL + "&client_id=" + propertySources.getClientID() + "&redirect_uri=" +
                                        propertySources.getRedirectUri() + "&scope=openid";

        return authorizationCodeURL;
    }

    /**
     * Receive oauth2 token from authentication server and store it in the app context
     *
     *
     * @param authorizationCode from authentication server
     * @return oauth2 token
     * @throws IOException
     */
    @Override
    public OAuth2AccessToken getAccessTokenByAuthCode(String authorizationCode) throws IOException {
        LOGGER.info("get oauth2 access token", TokenServiceImpl.class);

        OAuthRequest oAuthRequest = new OAuthRequest(Verb.POST, ACCESS_TOKEN_URI);
        oAuthRequest.addHeader("Content-Type", HEADER_CONTENT_TYPE);
        oAuthRequest.addHeader("Authorization", HEADER_AUTHORIZATION_TYPE + Base64.encodeBase64String(new String
                                    (propertySources.getClientID() + ":" + propertySources.getClientSecret()).getBytes()));
        oAuthRequest.setCharset(HEADER_CHAR_SET_TYPE);
        oAuthRequest.addBodyParameter("grant_type", AUTHORIZATION_CODE_GRANT_TYPE);
        oAuthRequest.addBodyParameter("code", authorizationCode);

        LOGGER.info("send request for access token", TokenServiceImpl.class);
        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(oAuthRequest.send().getBody());

        if (accessToken != null) {
            LOGGER.info("successfully received authorization token ");
            // store accessToken in session context
            this.accessToken = accessToken;
        } else {
            LOGGER.info("failed to get authorization token", TokenServiceImpl.class);
        }

        return accessToken;
    }

    /**
     * Get Access Token from session context
     *
     *
     * @return OAuth2AccessToken
     */
    @Override
    public OAuth2AccessToken getAccessTokenFromContext() {
        LOGGER.info("Get oauth2 access token from session context", TokenServiceImpl.class);

        return this.accessToken;
    }

    /**
     * Get HttpEntity that contains HttpHeader with Access Token
     *
     *
     * @return request HttpEntity with Access Token
     */
    @Override
    public HttpEntity getRequestHttpEntityWithAccessToken() {
        LOGGER.info("Get HttpEntity with Access Token", TokenServiceImpl.class);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + new JSONObject(getAccessTokenFromContext().getValue()).get("access_token"));
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);

        return requestEntity;
    }

}
