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

    private final String HEADER_FRAME_OPTIONS_ALLOW_FROM = "ALLOW-FROM  https://sandbox.tradeshift.com";

    /**
     * Allow to change security configuration
     *
     * Disable default security configuration
     * Disable default header frame options
     * Add to response header frame option 'ALLOW-FROM' for permits the specified 'uri' to frame
     *
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .httpBasic().disable()
                .headers().frameOptions().disable()
                .addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS", HEADER_FRAME_OPTIONS_ALLOW_FROM));

    }

}