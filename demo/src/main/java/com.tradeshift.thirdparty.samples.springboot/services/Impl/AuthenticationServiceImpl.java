package com.tradeshift.thirdparty.samples.springboot.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeshift.thirdparty.samples.springboot.domain.pojo.Oauth2TokenResponse;
import com.tradeshift.thirdparty.samples.springboot.services.AuthenticationService;
import org.apache.commons.codec.binary.Base64;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    static Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

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

    @Override
    public String getAuthorizationCodeURL() {
        String authorizationCodeURL = AUTHORIZE_URL + "&client_id=" + clientID + "&redirect_uri=" + redirectUri + "&scope=openid";

        return authorizationCodeURL;
    }

    @Override
    public String getAuthorizationToken(String authorizationCode) throws IOException {
        OAuthRequest oAuthRequest = new OAuthRequest(Verb.POST, accessTokenUri);
        oAuthRequest.addHeader("Content-Type", CONTENT_TYPE);
        oAuthRequest.addHeader("Authorization", AUTHORIZATION + Base64.encodeBase64String(new String(clientID + ":" + clientSecret).getBytes()));
        oAuthRequest.setCharset(CHAR_SET);
        oAuthRequest.addBodyParameter("grant_type", AUTHORIZATION_CODE_GRANT_TYPE);
        oAuthRequest.addBodyParameter("code", authorizationCode);

        LOGGER.info("send request for access token");
        String tokenResponse = oAuthRequest.send().getBody();

        if (tokenResponse != null && !tokenResponse.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            Oauth2TokenResponse oauth2TokenResponse = mapper.readValue(tokenResponse, Oauth2TokenResponse.class);
            if (oauth2TokenResponse.getAccess_token() != null && !oauth2TokenResponse.getAccess_token().isEmpty()) {

                LOGGER.info("successfully get authorization token ");
                return "success get token";
            }
        }

        LOGGER.info("failed get token");
        return "failed get token";
    }
}
