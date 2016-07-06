package com.tradeshift.thirdparty.samples.springboot.domain.dto;


public class BaseDocumentDTO {

    private String docType;
    private String docId;
    private Float docTotal;
    private String docCurrency;
    private String docIssueDate;
    private String docReceiverCompanyName;
    private String state;

    public BaseDocumentDTO() {
        super();
    }

    public BaseDocumentDTO(String docType, String docId, Float docTotal, String docCurrency, String docIssueDate, String docReceiverCompanyName, String state) {
        this.docType = docType;
        this.docId = docId;
        this.docTotal = docTotal;
        this.docCurrency = docCurrency;
        this.docIssueDate = docIssueDate;
        this.docReceiverCompanyName = docReceiverCompanyName;
        this.state = state;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Float getDocTotal() {
        return docTotal;
    }

    public void setDocTotal(Float docTotal) {
        this.docTotal = docTotal;
    }

    public String getDocCurrency() {
        return docCurrency;
    }

    public void setDocCurrency(String docCurrency) {
        this.docCurrency = docCurrency;
    }

    public String getDocIssueDate() {
        return docIssueDate;
    }

    public void setDocIssueDate(String docIssueDate) {
        this.docIssueDate = docIssueDate;
    }

    public String getDocReceiverCompanyName() {
        return docReceiverCompanyName;
    }

    public void setDocReceiverCompanyName(String docReceiverCompanyName) {
        this.docReceiverCompanyName = docReceiverCompanyName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
