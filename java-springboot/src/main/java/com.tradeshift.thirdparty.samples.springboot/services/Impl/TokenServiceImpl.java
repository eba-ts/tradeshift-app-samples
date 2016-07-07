package com.tradeshift.thirdparty.samples.springboot.services.Impl;

import com.tradeshift.thirdparty.samples.springboot.services.TokenService;
import org.apache.commons.codec.binary.Base64;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TokenServiceImpl implements TokenService {


    private OAuth2AccessToken accessToken = null;

    static Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    private final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private final String AUTHORIZATION = "Basic ";
    private final String CHAR_SET = "utf-8";
    private final String AUTHORIZATION_CODE_GRANT_TYPE = "authorization_code";

    @Value("${userAuthorizationUri}")
    private String AUTHORIZE_URL;

    @Value("${accessTokenUri}")
    private String accessTokenUri;

    @Value("#{systemEnvironment['clientID']}")
    private String clientID;

    @Value("#{systemEnvironment['clientSecret']}")
    private String clientSecret;

    @Value("${redirectUri}")
    private String redirectUri;

    /**
     * Get authorization server url for get authorization code
     *
     * @return URL for oauth2 authorization
     */
    @Override
    public String getAuthorizationCodeURL() {
        LOGGER.info("get authorization url", TokenServiceImpl.class);
        LOGGER.info("clientID is " + clientID);
        LOGGER.info("clientSecret is " + clientID);

        String authorizationCodeURL = AUTHORIZE_URL + "&client_id=" + clientID + "&redirect_uri=" +
                                        redirectUri + "&scope=openid";

        return authorizationCodeURL;
    }

    /**
     * Receive oauth2 token from authentication server and store it in the app context
     *
     * @param authorizationCode from authentication server
     * @return oauth2 token
     * @throws IOException
     */
    @Override
    public OAuth2AccessToken getAccessToken(String authorizationCode) throws IOException {
        LOGGER.info("get oauth2 access token", TokenServiceImpl.class);

        OAuthRequest oAuthRequest = new OAuthRequest(Verb.POST, accessTokenUri);
        oAuthRequest.addHeader("Content-Type", CONTENT_TYPE);
        oAuthRequest.addHeader("Authorization", AUTHORIZATION + Base64.encodeBase64String(new String(clientID + ":" +
                                    clientSecret).getBytes()));
        oAuthRequest.setCharset(CHAR_SET);
        oAuthRequest.addBodyParameter("grant_type", AUTHORIZATION_CODE_GRANT_TYPE);
        oAuthRequest.addBodyParameter("code", authorizationCode);

        LOGGER.info("send request for access token", TokenServiceImpl.class);
        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(oAuthRequest.send().getBody());

        if (accessToken != null) {
            LOGGER.info("successfully get authorization token ");
            // store accessToken in app context
            this.accessToken = accessToken;
        } else {
            LOGGER.info("failed get authorization token", TokenServiceImpl.class);
        }

        return accessToken;
    }

    @Override
    public OAuth2AccessToken getAccessToken() {
        return this.accessToken;
    }

    @Override
    public void setAccessToken(OAuth2AccessToken accessToken) {
        this.accessToken = accessToken;
    }

}
