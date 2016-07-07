package com.tradeshift.thirdparty.samples.springboot.controllers;

import com.tradeshift.thirdparty.samples.springboot.services.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/oauth2")
public class TokenController {

    static Logger LOGGER = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    TokenService tokenService;

    /**
     * Redirecting to authorization server for get authorization code
     *
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/get_token", method = RequestMethod.GET)
    public void getAuthorizationCode(final HttpServletResponse response) throws IOException {
        LOGGER.info("redirect to the authorization server", TokenController.class);

        response.sendRedirect(tokenService.getAuthorizationCodeURL());
    }

    /**
     * Get oauth2 access token by authorization code and store it into session TokenService
     *
     *
     * @param code authorization code from authorization server
     * @throws IOException
     */
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public void codeResponse(@RequestParam(value = "code", required = true) String code, final HttpServletResponse response) throws IOException {
        LOGGER.info("get authorization token by authorization code", TokenController.class);

        OAuth2AccessToken accessToken = tokenService.getAccessToken(code);
        if (accessToken != null) {
            LOGGER.info("get authorization token by authorization code success", TokenController.class);
        } else {
            LOGGER.warn("get authorization token by authorization code failed", TokenController.class);
        }

        response.sendRedirect("/");
    }
}