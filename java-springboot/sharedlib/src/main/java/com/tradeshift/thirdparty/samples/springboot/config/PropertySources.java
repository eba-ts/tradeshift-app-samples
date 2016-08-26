package com.tradeshift.thirdparty.samples.springboot.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration(value = "propertySources")
public class PropertySources {

    private String tradeshiftAPIDomainName;
    private String clientID;
    private String clientSecret;
    private String redirectUri;
    private String tradeshiftAppVersion;

    @PostConstruct
    public void PropertySourcesInit() {
        this.tradeshiftAppVersion = System.getenv("TRADESHIFT_APP_VERSION");
        this.tradeshiftAPIDomainName = System.getenv("tradeshiftAPIDomainName");
        this.clientID = System.getenv("clientID");
        this.clientSecret = System.getenv("clientSecret");
        this.redirectUri = System.getenv("redirectUri");
    }

    public String getTradeshiftAppVersion() {
        return tradeshiftAppVersion;
    }

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
