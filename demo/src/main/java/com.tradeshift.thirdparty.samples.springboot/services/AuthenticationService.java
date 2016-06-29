package com.tradeshift.thirdparty.samples.springboot.services;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface AuthenticationService {

    String getAuthorizationCodeURL();

    String getAuthorizationToken(String authorizationCode) throws IOException;
}