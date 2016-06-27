package com.example.services.Impl;

import com.example.services.AuthenticationService;
import org.apache.commons.codec.binary.Base64;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${userAuthorizationUri}")
    private String AUTHORIZE_URL;

    @Value("${accessTokenUri}")
    private String accessTokenUri;

    @Value("${clientID}")
    private String clientID;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${vendorID}")
    private String vendorId;

    @Value("${redirectUri}")
    private String redirectUri;

    @Override
    public String getAuthorizationCodeURL() {
        StringBuilder sb = new StringBuilder();
        sb.append(AUTHORIZE_URL);
        sb.append("&client_id=");
        sb.append(clientID);
        sb.append("&redirect_uri=");
        sb.append(redirectUri);
        sb.append("&scope=openid");

        return sb.toString();
    }

    @Override
    public String getAuthorizationToken(String authorizationCode) {
        OAuthRequest request = new OAuthRequest(Verb.POST, accessTokenUri);
        request.addHeader("Content-type", "application/x-www-form-urlencoded");

        request.addBodyParameter("Authorization","Basic" + Base64.encodeBase64String(new String(clientID + ":" +
                clientSecret).getBytes()));
        request.addBodyParameter("grant_type", "authorization_code");
        request.addBodyParameter("code", authorizationCode);
        Response response = request.send();
        return response.getBody().toString();
    }
}
