package com.tradeshift.thirdparty.samples.springboot.domain.dto;


public class WebhookEventDTO {

    private String id;
    private String tsUserId;
    private String tsCompanyAccountId;
    private String tsUserLanguage;
    private String event;

    public WebhookEventDTO() {
        super();
    }

    public WebhookEventDTO(String id, String tsUserId, String tsCompanyAccountId, String tsUserLanguage, String event) {
        this.id = id;
        this.tsUserId = tsUserId;
        this.tsCompanyAccountId = tsCompanyAccountId;
        this.tsUserLanguage = tsUserLanguage;
        this.event = event;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTsUserId() {
        return tsUserId;
    }

    public void setTsUserId(String tsUserId) {
        this.tsUserId = tsUserId;
    }

    public String getTsCompanyAccountId() {
        return tsCompanyAccountId;
    }

    public void setTsCompanyAccountId(String tsCompanyAccountId) {
        this.tsCompanyAccountId = tsCompanyAccountId;
    }

    public String getTsUserLanguage() {
        return tsUserLanguage;
    }

    public void setTsUserLanguage(String tsUserLanguage) {
        this.tsUserLanguage = tsUserLanguage;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "WebhookEventDTO{" +
                "id='" + id + '\'' +
                ", tsUserId='" + tsUserId + '\'' +
                ", tsCompanyAccountId='" + tsCompanyAccountId + '\'' +
                ", tsUserLanguage='" + tsUserLanguage + '\'' +
                ", event='" + event + '\'' +
                '}';
    }
}
