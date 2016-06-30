package com.tradeshift.thirdparty.samples.springboot.controllers;

import com.tradeshift.thirdparty.samples.springboot.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/oauth2")
public class TokenController {

    @Autowired
    TokenService authenticationService;

    @RequestMapping(value = "/get_token", method = RequestMethod.GET)
    public void getAuthorizationCode(final HttpServletResponse response) throws IOException {
        response.sendRedirect(authenticationService.getAuthorizationCodeURL());
    }

    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public String codeResponse(@RequestParam(value = "code", required = true) String code) throws IOException {
        return authenticationService.getAuthorizationToken(code).getValue();
    }
}