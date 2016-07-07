package com.tradeshift.thirdparty.samples.springboot.services;


import com.tradeshift.thirdparty.samples.springboot.domain.dto.BaseDocumentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Service
public interface DocumentsService {

    List<BaseDocumentDTO> getDocuments(String documentType) throws ParserConfigurationException, IOException, SAXException;

    List<BaseDocumentDTO> parseDocuments(ResponseEntity responseEntity) throws ParserConfigurationException, IOException, SAXException;
}
