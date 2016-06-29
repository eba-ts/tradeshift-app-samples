package com.tradeshift.thirdparty.samples.springboot.controllers;

import com.tradeshift.thirdparty.samples.springboot.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value = "/tradeshift", method = RequestMethod.GET)
    public void getAuthorizationCode(final HttpServletResponse response) throws IOException {
        response.sendRedirect(authenticationService.getAuthorizationCodeURL());
    }

    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public String codeResponse(@RequestParam(value = "code", required = true) String code) throws IOException {
        return authenticationService.getAuthorizationToken(code);
    }
}