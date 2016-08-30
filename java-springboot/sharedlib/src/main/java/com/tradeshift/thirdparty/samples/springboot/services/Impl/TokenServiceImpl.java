package com.tradeshift.thirdparty.samples.springboot.services.Impl;

import com.nimbusds.jwt.PlainJWT;
import com.tradeshift.thirdparty.samples.springboot.config.PropertySources;
import com.tradeshift.thirdparty.samples.springboot.domain.dto.JwtDTO;
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
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;


@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TokenServiceImpl implements TokenService {

    static Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    private final String HEADER_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private final String HEADER_AUTHORIZATION_TYPE = "Basic ";
    private final String HEADER_CHAR_SET_TYPE = "utf-8";
    private final String AUTHORIZATION_CODE_GRANT_TYPE = "authorization_code";
    private final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
    private final String AUTHORIZE_URL;
    private final String ACCESS_TOKEN_URI;
    private final String REDIRECT_URI;
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;

    private OAuth2AccessToken accessToken;
    private PropertySources propertySources;
    private String userId;
    private JwtDTO jwtDTO;


    /**
     * Inject PropertySources bean by constructor,
     * Init AUTHORIZE_URL, ACCESS_TOKEN_URI, CLIENT_ID, REDIRECT_URI, CLIENT_SECRET
     *
     * @param propertySources
     */
    @Autowired
    public TokenServiceImpl(@Qualifier("propertySources") PropertySources propertySources) {
        super();
        this.propertySources = propertySources;

        this.AUTHORIZE_URL = propertySources.getTradeshiftAPIDomainName() + "/tradeshift/auth/login?response_type=code";
        this.ACCESS_TOKEN_URI = propertySources.getTradeshiftAPIDomainName() + "/tradeshift/auth/token";
        this.CLIENT_ID = propertySources.getClientID();
        this.REDIRECT_URI = propertySources.getRedirectUri();
        this.CLIENT_SECRET = propertySources.getClientSecret();
    }

    /**
     * Get authorization server url for to get authorization code
     *
     * @return URL for oauth2 authorization
     */
    @Override
    public String getAuthorizationCodeURL() {
        LOGGER.info("get authorization url", TokenServiceImpl.class);

        String authorizationCodeURL = AUTHORIZE_URL + "&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&scope=offline";

        return authorizationCodeURL;
    }

    /**
     * Receive oauth2 token from authentication server and store it in the session context
     *
     * @param authorizationCode from authentication server
     * @return oauth2 token
     * @throws IOException
     */
    @Override
    public OAuth2AccessToken getAccessTokenByAuthCode(String authorizationCode) throws ParseException {
        LOGGER.info("get oauth2 access token", TokenServiceImpl.class);

        OAuthRequest oAuthRequest = new OAuthRequest(Verb.POST, ACCESS_TOKEN_URI);
        oAuthRequest.addHeader("Content-Type", HEADER_CONTENT_TYPE);
        oAuthRequest.addHeader("Authorization", HEADER_AUTHORIZATION_TYPE + Base64.encodeBase64String(new String(CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));
        oAuthRequest.setCharset(HEADER_CHAR_SET_TYPE);
        oAuthRequest.addBodyParameter("grant_type", AUTHORIZATION_CODE_GRANT_TYPE);
        oAuthRequest.addBodyParameter("code", authorizationCode);

        LOGGER.info("send request for access token", TokenServiceImpl.class);
        String accessTokenResponse = oAuthRequest.send().getBody();

        if (accessTokenResponse != null) {
            LOGGER.info("successfully received authorization token");
            // store accessToken in session context
            this.accessToken = new DefaultOAuth2AccessToken(new JSONObject(accessTokenResponse).get("access_token").toString());
            ((DefaultOAuth2AccessToken) this.accessToken).setRefreshToken(new DefaultOAuth2RefreshToken(new JSONObject(accessTokenResponse).get("refresh_token").toString()));
            ((DefaultOAuth2AccessToken) this.accessToken).setExpiration((new Date(System.currentTimeMillis() + (Long
                    .valueOf(new JSONObject(accessTokenResponse).get("expires_in").toString()) * 60000))));

            String idToken = new JSONObject(accessTokenResponse).get("id_token").toString();
            this.jwtDTO = parseJWTToken(idToken);
            this.userId = this.jwtDTO.getUserId();

        } else {
            LOGGER.warn("failed to get authorization token", TokenServiceImpl.class);
        }

        return accessToken;
    }

    /**
     * Obtain a new token by refresh token
     */
    @Override
    public void refreshToken() {
        if (this.accessToken.getRefreshToken() != null) {
            OAuthRequest oAuthRequest = new OAuthRequest(Verb.POST, ACCESS_TOKEN_URI);
            oAuthRequest.addHeader("Content-Type", HEADER_CONTENT_TYPE);
            oAuthRequest.addHeader("Authorization", HEADER_AUTHORIZATION_TYPE + Base64.encodeBase64String(new String(CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));
            oAuthRequest.setCharset(HEADER_CHAR_SET_TYPE);
            oAuthRequest.addBodyParameter("grant_type", REFRESH_TOKEN_GRANT_TYPE);
            oAuthRequest.addBodyParameter("refresh_token", getAccessTokenFromContext().getRefreshToken().getValue().toString());
            oAuthRequest.addBodyParameter("scope", CLIENT_ID + "." + propertySources.getTradeshiftAppVersion());

            LOGGER.info("send request for access token by refresh token", TokenServiceImpl.class);

            String accessTokenResponse = oAuthRequest.send().getBody();

            if (accessTokenResponse != null) {
                LOGGER.info("successfully received authorization token by refresh token");
                // store accessToken in session context
                this.accessToken = new DefaultOAuth2AccessToken(new JSONObject(accessTokenResponse).get("access_token").toString());
                ((DefaultOAuth2AccessToken) this.accessToken).setExpiration((new Date(System.currentTimeMillis() + Long.valueOf(new JSONObject(accessTokenResponse).get("expires_in").toString()))));
            } else {
                LOGGER.warn("failed to get authorization token by refresh token", TokenServiceImpl.class);
            }
        } else {
            LOGGER.error("Refresh token doesn't exist", TokenServiceImpl.class);
        }

    }

    /**
     * Get Access Token from session context
     *
     * @return OAuth2AccessToken
     */
    @Override
    public OAuth2AccessToken getAccessTokenFromContext() {
        LOGGER.info("get oauth2 access token from session context", TokenServiceImpl.class);

        return this.accessToken;
    }

    /**
     * Get HttpEntity that contains HttpHeader with Access Token
     *
     * @return request HttpEntity with Access Token
     */
    @Override
    public HttpEntity getRequestHttpEntityWithAccessToken() {
        LOGGER.info("get HttpEntity with Access Token", TokenServiceImpl.class);

        if (getAccessTokenFromContext().isExpired()) {
            refreshToken();
        }

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + getAccessTokenFromContext().getValue());
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);

        return requestEntity;
    }

    /**
     * Get current user Id from session context
     *
     * @return userId
     */
    @Override
    public String getCurrentUserId() {
        return userId;
    }

    /**
     * Get tradeshift JWT token info
     *
     * @return JwtDTO
     */
    @Override
    public JwtDTO JwtDTO() {
        return jwtDTO;
    }

    /**
     * Convert tradeshift JWT token to JwtDTO object
     *
     * @param idToken Tradeshift JWT token
     * @return JwtDTO
     * @throws ParseException
     */
    private JwtDTO parseJWTToken(String idToken) throws ParseException {
        JwtDTO jwtDTO = new JwtDTO();

        net.minidev.json.JSONObject jwtJson = PlainJWT.parse(idToken).getPayload().toJSONObject();
        jwtDTO.setOriginalJWTToken(idToken);
        jwtDTO.setUserId(jwtJson.get("userId").toString());
        jwtDTO.setTradeshiftUserEmail(jwtJson.get("sub").toString());
        jwtDTO.setAppName(jwtJson.get("aud").toString());
        jwtDTO.setCompanyId(jwtJson.get("companyId").toString());
        jwtDTO.setIss(jwtJson.get("iss").toString());
        jwtDTO.setExpirationTime(Long.valueOf(jwtJson.get("exp").toString()));
        jwtDTO.setIssuedAtTime(Long.valueOf(jwtJson.get("iat").toString()));
        jwtDTO.setJwtUniqueIdentifier(jwtJson.get("jti").toString());

        return jwtDTO;
    }

}
