package com.tradeshift.thirdparty.samples.springboot.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;


@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String HEADER_FRAME_OPTIONS_ALLOW_FROM = "ALLOW-FROM  https://shop.sandbox.tradeshift.com";

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //disable base security
        http
                .httpBasic().disable().headers().frameOptions().disable().addHeaderWriter(new StaticHeadersWriter
                ("X-FRAME-OPTIONS", HEADER_FRAME_OPTIONS_ALLOW_FROM));


    }

}