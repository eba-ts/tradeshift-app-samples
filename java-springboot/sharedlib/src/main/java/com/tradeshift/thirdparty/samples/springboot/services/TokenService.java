package com.tradeshift.thirdparty.samples.springboot.services;

import org.springframework.http.HttpEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.text.ParseException;

@Service
public interface TokenService {

    String getAuthorizationCodeURL();

    OAuth2AccessToken getAccessTokenByAuthCode(String authorizationCode) throws ParseException;

    void refreshToken();

    OAuth2AccessToken getAccessTokenFromContext();

    HttpEntity getRequestHttpEntityWithAccessToken();

    String getCurrentUserId() throws ParserConfigurationException;
}