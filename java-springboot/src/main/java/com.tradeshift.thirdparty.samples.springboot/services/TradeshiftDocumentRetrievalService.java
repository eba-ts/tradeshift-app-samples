package com.tradeshift.thirdparty.samples.springboot.services;


import com.tradeshift.thirdparty.samples.springboot.domain.dto.BaseTradeshiftDocumentDTO;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Service
public interface TradeshiftDocumentRetrievalService {

    List<BaseTradeshiftDocumentDTO> getDocuments(String documentType) throws ParserConfigurationException, IOException, SAXException;

}
