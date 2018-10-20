package com.tradeshift.thirdparty.samples.springboot.services.Impl;


import com.tradeshift.thirdparty.samples.springboot.domain.dto.WebhookEventDTO;
import com.tradeshift.thirdparty.samples.springboot.services.WebHooksService;
import com.tradeshift.thirdparty.springboot.samples.services.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebHooksServiceImpl implements WebHooksService {

    static Logger LOGGER = LoggerFactory.getLogger(WebHooksServiceImpl.class);


    @Autowired
    TokenService tokenService;

    private final Map<String, List<WebhookEventDTO>> messages = Collections.synchronizedMap(new HashMap<String, List<WebhookEventDTO>>());

    /**
     * Send message to angularjs client
     *
     * @return SseEmitter
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public SseEmitter sendWebhookEvent() throws ParserConfigurationException, SAXException, IOException {
        List<WebhookEventDTO> eventDTOList = messages.get(tokenService.getCurrentUserId());
        SseEmitter emitter = new SseEmitter(60000L);

        if (eventDTOList != null) {
            new Thread(new RunProcess(eventDTOList , emitter, tokenService.getCurrentUserId())).start();
        }

        return emitter;
    }

    /**
     * Add to messages new webhook event
     *
     * @param id event id
     * @param tsUserId user Id who received the new event
     * @param tsCompanyAccountId Company Account Id
     * @param tsUserLanguage selected user language
     * @param event event type
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public void addWebhookEvent(String id, String tsUserId, String tsCompanyAccountId, String tsUserLanguage, String event) throws ParserConfigurationException, SAXException, IOException {
        LOGGER.info("add document event", WebHooksServiceImpl.class);
        synchronized (messages) {
            if (!messages.containsKey(tsUserId)) {
                List<WebhookEventDTO> webhookEventDTOs = new ArrayList<WebhookEventDTO>();
                webhookEventDTOs.add(new WebhookEventDTO(id, tsUserId, tsCompanyAccountId, tsUserLanguage, event));
                messages.put(tsUserId, webhookEventDTOs);
            } else {
                messages.get(tsUserId).add(new WebhookEventDTO(id, tsUserId, tsCompanyAccountId, tsUserLanguage, event));
            }
        }
    }

    private class RunProcess implements Runnable {

        private SseEmitter sseEmitter;
        private List<WebhookEventDTO> message;
        private String userId;

        RunProcess(List<WebhookEventDTO> message, SseEmitter sseEmitter, String userId) {
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
