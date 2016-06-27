package com.example.controllers;

import com.example.services.AuthenticationService;
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

    @RequestMapping("/")
    public void getAuthorizationCode(final HttpServletResponse response) throws IOException {
        response.sendRedirect(authenticationService.getAuthorizationCodeURL());
    }

    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public String codeResponse(@RequestParam(value = "code", required = false) String code) {
        System.out.println(code);
        return authenticationService.getAuthorizationToken(code);
    }
}
