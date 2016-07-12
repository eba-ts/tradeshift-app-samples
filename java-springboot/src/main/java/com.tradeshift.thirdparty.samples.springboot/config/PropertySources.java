package com.tradeshift.thirdparty.samples.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "propertySources")
public class PropertySources {

    @Value("#{systemEnvironment['tradeshiftAPIDomainName']}")
    private String tradeshiftAPIDomainName;

    @Value("#{systemEnvironment['clientID']}")
    private String clientID;

    @Value("#{systemEnvironment['clientSecret']}")
    private String clientSecret;

    @Value("#{systemEnvironment['redirectUri']}")
    private String redirectUri;

    public String getTradeshiftAPIDomainName() {
        return tradeshiftAPIDomainName;
    }

    public String getClientID() {
        return clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}
