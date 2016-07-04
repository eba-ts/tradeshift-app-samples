package com.tradeshift.thirdparty.samples.springboot.services;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface TokenService {

    String getAuthorizationCodeURL();

    OAuth2AccessToken getAuthorizationToken(String authorizationCode) throws IOException;
}