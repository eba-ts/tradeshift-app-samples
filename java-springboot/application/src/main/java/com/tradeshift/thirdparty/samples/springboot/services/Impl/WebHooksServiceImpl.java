package com.tradeshift.thirdparty.samples.springboot.services.Impl;


import com.tradeshift.thirdparty.samples.springboot.services.TokenService;
import com.tradeshift.thirdparty.samples.springboot.services.WebHooksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebHooksServiceImpl implements WebHooksService {

    static Logger LOGGER = LoggerFactory.getLogger(WebHooksServiceImpl.class);


    @Autowired
    TokenService tokenService;

    private final Map<String, Integer> messages = Collections.synchronizedMap(new HashMap<String, Integer>());

    /**
     * Send message to angularjs client
     *
     * @return SseEmitter
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public SseEmitter sendDocsEvent() throws ParserConfigurationException, SAXException, IOException {
        Integer newDocsCount = messages.get(tokenService.getCurrentUserId());
        SseEmitter emitter = new SseEmitter(60000L);

        if (newDocsCount != null) {
            new Thread(new RunProcess("You received " + messages.size() + " new  documents", emitter, tokenService.getCurrentUserId())).start();
        }

        return emitter;
    }

    /**
     * Add to messages new document event
     *
     * @param userId UUID user from tradeshift webhook who received new document
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public void addDocumentEvent(String userId) throws ParserConfigurationException, SAXException, IOException {
        LOGGER.info("add document event", WebHooksServiceImpl.class);
        synchronized (messages) {
            if (!messages.containsKey(userId)) {
                messages.put(userId, 1);
            } else {
                messages.put(userId, messages.get(userId) + 1);
            }
        }
    }

    private class RunProcess implements Runnable {

        private SseEmitter sseEmitter;
        private String message;
        private String userId;

        RunProcess(String message, SseEmitter sseEmitter, String userId) {
            this.message = message;
            this.sseEmitter = sseEmitter;
            this.userId = userId;
        }

        public void run() {
            try {
                LOGGER.info("Send message ", WebHooksServiceImpl.class);
                sseEmitter.send(message);
                sseEmitter.complete();
                messages.remove(userId);
            } catch (IOException e) {
                e.printStackTrace();
                sseEmitter.completeWithError(e);
            }
        }
    }

}
