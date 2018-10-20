package com.tradeshift.thirdparty.samples.springboot.controllers;

import com.tradeshift.thirdparty.springboot.samples.services.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/jwt")
public class JWTTokenController {

    static Logger LOGGER = LoggerFactory.getLogger(JWTTokenController.class);

    @Autowired
    TokenService tokenService;

    /**
     * Get parsed JWT token info
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/id-token", method = RequestMethod.GET)
    public ResponseEntity getJWTTokenInfo() {
        LOGGER.info("Get Tradeshift JWT Token Info", JWTTokenController.class);

        return new ResponseEntity(tokenService.JwtDTO(), HttpStatus.OK);
    }
}
