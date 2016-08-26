package com.tradeshift.thirdparty.samples.springboot.controllers;

import com.tradeshift.thirdparty.samples.springboot.services.WebHooksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Controller
@RequestMapping("/webhooks")
public class WebHooksController {

    static Logger LOGGER = LoggerFactory.getLogger(WebHooksController.class);


    @Autowired
    WebHooksService webHooksService;


    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public void receive(@RequestParam("id") final String id,
                        @RequestParam("tsUserId") final String tsUserId,
                        @RequestParam("tsCompanyAccountId") final String tsCompanyAccountId,
                        @RequestParam("tsUserLanguage") final String tsUserLanguage,
                        @RequestParam("event") final String event ) throws IOException, SAXException, ParserConfigurationException {

        LOGGER.info("Receive document event from tradeshift");
        LOGGER.info("Document Id " + id + ", User id +" + tsUserId + ", CompanyAccountId "
                                + tsCompanyAccountId + ", User " + "Language " + tsUserLanguage);

        webHooksService.addDocumentEvent(tsUserId);
    }

    @RequestMapping(value = "/eventsStatus", method = RequestMethod.GET)
    public SseEmitter EventsStatus(HttpServletResponse response) throws IOException, ParserConfigurationException, SAXException {
        LOGGER.info("Server sent event");

        return webHooksService.sendDocsEvent();
    }
}
