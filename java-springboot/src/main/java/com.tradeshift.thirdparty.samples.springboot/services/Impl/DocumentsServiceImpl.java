package com.tradeshift.thirdparty.samples.springboot.services.Impl;

import com.tradeshift.thirdparty.samples.springboot.domain.dto.BaseDocumentDTO;
import com.tradeshift.thirdparty.samples.springboot.services.DocumentsService;
import com.tradeshift.thirdparty.samples.springboot.services.TokenService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
public class DocumentsServiceImpl implements DocumentsService {

    private final static String URI_LIST_DOCUMENTS = "tradeshift/rest/external/documents";


    @Autowired
    TokenService tokenService;

    @Value("${restServiceUri}")
    private String URI_REST_SERVICE;


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
    @Override
    public List<BaseDocumentDTO> getDocuments(String documentType) throws IOException, SAXException, ParserConfigurationException {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + new JSONObject(tokenService.getAccessToken().getValue()).get("access_token"));

        Map<String, String> uriParams = new HashMap<String, String>();
        uriParams.put("type", documentType);

        String documentsUri = URI_REST_SERVICE + URI_LIST_DOCUMENTS;

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);
        ResponseEntity<?> responseEntity = restTemplate.exchange(documentsUri, HttpMethod.GET,
                                                        requestEntity, String.class, uriParams);

        return parseDocuments(responseEntity);
    }

    /**
     * Convert list of documents from UBL format to list BaseDocumentDTO
     *
     *
     * @param responseEntity ResponseEntity with list of documents in the UBL format
     * @return List of documents converted to List<BaseDocumentDTO>
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     */
    @Override
    public List<BaseDocumentDTO> parseDocuments(ResponseEntity responseEntity) throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new ByteArrayInputStream(responseEntity.getBody().toString().getBytes()));

        NodeList nList = doc.getElementsByTagName("ts:Document");

        List<BaseDocumentDTO> documentDTOs = new ArrayList<BaseDocumentDTO>();
        for (int i = 0; i < nList.getLength(); i++) {
            Element rootDocElement = (Element) nList.item(i);
            Element elementDocType = (Element) rootDocElement.getElementsByTagName("ts:DocumentType").item(0);

            String docCurrency = rootDocElement.getElementsByTagName("ts:ItemInfos").item(0).getChildNodes().item(5)
                                                .getTextContent();
            String docIssueDate = rootDocElement.getElementsByTagName("ts:ItemInfos").item(0).getChildNodes().item(7)
                                                .getTextContent();
            String docReceiverCompName = rootDocElement.getElementsByTagName("ts:ReceiverCompanyName").item(0)
                                                .getTextContent();
            Float docTotal = Float.valueOf(rootDocElement.getElementsByTagName("ts:ItemInfos").item(0).getChildNodes().item(3)
                                                .getTextContent());

            String docType = elementDocType.getAttribute("type");
            String state = rootDocElement.getElementsByTagName("ts:UnifiedState").item(0).getTextContent();
            String docId = "#" + rootDocElement.getElementsByTagName("ts:ID").item(0).getTextContent();


            documentDTOs.add(new BaseDocumentDTO(docType, docId, docTotal, docCurrency, docIssueDate, docReceiverCompName, state));

        }

        return documentDTOs;
    }

}
