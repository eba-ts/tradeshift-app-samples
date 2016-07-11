package com.tradeshift.thirdparty.samples.springboot.services.Impl;

import com.tradeshift.thirdparty.samples.springboot.config.PropertySources;
import com.tradeshift.thirdparty.samples.springboot.domain.dto.BaseTradeshiftDocumentDTO;
import com.tradeshift.thirdparty.samples.springboot.services.TokenService;
import com.tradeshift.thirdparty.samples.springboot.services.TradeshiftDocumentRetrievalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TradeshiftDocumentRetrievalServiceImpl implements TradeshiftDocumentRetrievalService {

    static Logger LOGGER = LoggerFactory.getLogger(TradeshiftDocumentRetrievalServiceImpl.class);

    @Autowired
    TokenService tokenService;

    private final String URI_LIST_DOCUMENTS;

    private PropertySources propertySources;

    /**
     * Inject PropertySources bean by constructor,
     * init URI_LIST_DOCUMENTS
     *
     *
     * @param propertySources
     */
    @Autowired
    public TradeshiftDocumentRetrievalServiceImpl(@Qualifier("propertySources") PropertySources propertySources) {
        super();
        this.propertySources = propertySources;
        URI_LIST_DOCUMENTS = propertySources.getTradeShiftAPIDomainName() + "/tradeshift/rest/external/documents";
    }


    /**
     * Get List of documents by document type
     *
     *
     * @param documentType documents type
     * @return List of documents by document type for current user
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public List<BaseTradeshiftDocumentDTO> getDocuments(String documentType) throws IOException, SAXException,
            ParserConfigurationException {

        LOGGER.info("get List of documents by document type " + documentType, TradeshiftDocumentRetrievalServiceImpl
                .class);

        Map<String, String> uriParams = new HashMap<String, String>();
        uriParams.put("type", documentType);

        String documentsUri = URI_LIST_DOCUMENTS;

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = tokenService.getRequestHttpEntityWithAccessToken();
        ResponseEntity<?> responseEntity = restTemplate.exchange(documentsUri, HttpMethod.GET,
                                                        requestEntity, String.class, uriParams);

        return parseDocuments(responseEntity);
    }

    /**
     * Convert list of documents from UBL format to list
     *
     *
     * @param responseEntity ResponseEntity with list of documents in the UBL format
     * @return List of documents converted to List<BaseTradeshiftDocumentDTO>
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     */
    protected List<BaseTradeshiftDocumentDTO> parseDocuments(ResponseEntity responseEntity) throws SAXException,
            ParserConfigurationException, IOException {

        LOGGER.info("parse Tradeshift Document", TradeshiftDocumentRetrievalServiceImpl.class);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new ByteArrayInputStream(responseEntity.getBody().toString().getBytes()));

        NodeList nList = doc.getElementsByTagName("ts:Document");

        List<BaseTradeshiftDocumentDTO> documentDTOs = new ArrayList<BaseTradeshiftDocumentDTO>();
        for (int i = 0; i < nList.getLength(); i++) {
            String companyName = null;
            Element rootDocElement = (Element) nList.item(i);
            Element elementDocType = (Element) rootDocElement.getElementsByTagName("ts:DocumentType").item(0);

            String docCurrency = rootDocElement.getElementsByTagName("ts:ItemInfos").item(0).getChildNodes().item(5)
                                                    .getTextContent();
            String docIssueDate = rootDocElement.getElementsByTagName("ts:ItemInfos").item(0).getChildNodes().item(7)
                                                    .getTextContent();

            NodeList nodeListSenderCompany = rootDocElement.getElementsByTagName("ts:SenderCompanyName");
            if (nodeListSenderCompany != null && nodeListSenderCompany.getLength() > 0) {
                companyName = nodeListSenderCompany.item(0).getTextContent();
            } else {
                NodeList nodeListReceiverCompany = rootDocElement.getElementsByTagName("ts:ReceiverCompanyName");
                companyName = nodeListReceiverCompany.item(0).getTextContent();
            }

            Float docTotal = Float.valueOf(rootDocElement.getElementsByTagName("ts:ItemInfos").item(0).getChildNodes().item(3)
                                                            .getTextContent());

            String docType = elementDocType.getAttribute("type");
            String state = rootDocElement.getElementsByTagName("ts:UnifiedState").item(0).getTextContent();
            String docId = "#" + rootDocElement.getElementsByTagName("ts:ID").item(0).getTextContent();

            documentDTOs.add(new BaseTradeshiftDocumentDTO(docType, docId, docTotal, docCurrency, docIssueDate, companyName, state));
        }

        return documentDTOs;
    }

}
