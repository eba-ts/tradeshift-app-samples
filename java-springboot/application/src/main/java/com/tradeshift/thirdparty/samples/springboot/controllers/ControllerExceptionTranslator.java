package com.tradeshift.thirdparty.samples.springboot.controllers;

import io.swagger.client.ApiException;
import org.json.JSONObject;
import org.scribe.exceptions.OAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ControllerExceptionTranslator {

    static Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionTranslator.class);


    /**
     * Handle HttpClientErrorException
     *
     * @param e
     * @return ResponseEntity with error message
     */
    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity httpClientHandler(HttpClientErrorException e) {
        LOGGER.error(e.getMessage(), e, ControllerExceptionTranslator.class);

        String message = new JSONObject().put("message", e.getMessage()).toString();

        return new ResponseEntity(message, e.getStatusCode());
    }

    /**
     * Handle ApiException
     *
     * @param e
     * @return ResponseEntity with error message
     */
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity ApiExceptionHandler(ApiException e) {
        LOGGER.error(e.getMessage(), e, ControllerExceptionTranslator.class);

        String message = null;
        if (!e.getResponseBody().isEmpty()) {
            message = new JSONObject().put("message", new JSONObject(e.getResponseBody()).get("Message")).toString();
        } else {
            message = new JSONObject().put("message", e.getMessage()).toString();
        }

        return new ResponseEntity(message, HttpStatus.valueOf(e.getCode()));
    }

    /**
     * Handle InvalidRequestException
     *
     * @param e
     * @return ResponseEntity with error message
     */
    @ExceptionHandler(value = InvalidRequestException.class)
    public ResponseEntity InvalidRequestExceptionHandler(InvalidRequestException e) {
        LOGGER.error(e.getMessage(), e, InvalidRequestException.class);

        return new ResponseEntity(new JSONObject().put("message", e.getMessage()).toString(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Handle OAuthException
     *
     * @param e
     * @return ResponseEntity with error message
     */
    @ExceptionHandler(value = OAuthException.class)
    public ResponseEntity OAuthExceptionHandler(OAuthException e) {
        LOGGER.error(e.getMessage(), e, ControllerExceptionTranslator.class);

        return new ResponseEntity(new JSONObject().put("message", e.getMessage()).toString(), HttpStatus.UNAUTHORIZED);
    }

}
