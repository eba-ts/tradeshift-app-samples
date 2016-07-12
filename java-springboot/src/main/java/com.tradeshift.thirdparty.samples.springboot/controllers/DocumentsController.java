package com.tradeshift.thirdparty.samples.springboot.controllers;


import com.tradeshift.thirdparty.samples.springboot.domain.dto.BaseTradeshiftDocumentDTO;
import com.tradeshift.thirdparty.samples.springboot.services.TokenService;
import com.tradeshift.thirdparty.samples.springboot.services.TradeshiftDocumentRetrievalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/document")
public class DocumentsController {

    static Logger LOGGER = LoggerFactory.getLogger(DocumentsController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    TradeshiftDocumentRetrievalService tradeshiftDocumentRetrievalService;

    /**
     * Get documents by document type from remote server by call to rest service
     *
     *
     * @param response
     * @return List of documents by document type from remote server
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public ResponseEntity<?> getDocument(@RequestParam("documentType") final String documentType, final HttpServletResponse response)
            throws ParserConfigurationException, IOException, SAXException {

        LOGGER.info("get list of documents by document type", DocumentsController.class);

        if (tokenService.getAccessTokenFromContext() != null) {
            LOGGER.info("succeed in to get list of documents by document type", DocumentsController.class);
            List<BaseTradeshiftDocumentDTO> result = tradeshiftDocumentRetrievalService.getDocuments(documentType);

            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            LOGGER.info("failed to get list of documents by document type, access token doesn't exist", DocumentsController.class);
            response.sendRedirect(tokenService.getAuthorizationCodeURL());

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

}
