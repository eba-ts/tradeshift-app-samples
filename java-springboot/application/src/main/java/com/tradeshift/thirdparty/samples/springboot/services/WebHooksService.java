package com.tradeshift.thirdparty.samples.springboot.services;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Service
public interface WebHooksService {

    SseEmitter sendDocsEvent() throws ParserConfigurationException, SAXException, IOException;

    void addDocumentEvent(String id, String tsUserId, String tsCompanyAccountId, String tsUserLanguage, String event)
                                                        throws ParserConfigurationException, SAXException, IOException;
}
