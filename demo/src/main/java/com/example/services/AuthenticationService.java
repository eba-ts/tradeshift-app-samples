package com.example.services;

import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

   String getAuthorizationCodeURL();

   String getAuthorizationToken(String authorizationCode);
}
