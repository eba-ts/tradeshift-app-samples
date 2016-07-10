package com.tradeshift.thirdparty.samples.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "propertySources")
public class PropertySources {

    @Value("#{systemEnvironment['tradeShiftAPIDomainName']}")
    private String tradeShiftAPIDomainName;

    @Value("#{systemEnvironment['clientID']}")
    private String clientID;

    @Value("#{systemEnvironment['clientSecret']}")
    private String clientSecret;

    @Value("#{systemEnvironment['redirectUri']}")
    private String redirectUri;

    public String getTradeShiftAPIDomainName() {
        return tradeShiftAPIDomainName;
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
