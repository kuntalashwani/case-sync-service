package com.example.casesyncservice.model;

import lombok.Data;

@Data
public class CaseItem {
    private String id;
    private String displayId;
    private String subject;
    private String priority;
    private String priorityDescription;
    private String origin;
    private String caseType;
    private String caseTypeDescription;
    private String statusSchema;
    private String status;
    private String statusDescription;
    private String escalationStatus;
    private boolean isRecommendedCommunicationLanguage;
    private Account account;
}
