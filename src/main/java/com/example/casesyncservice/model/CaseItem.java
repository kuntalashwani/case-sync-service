package com.example.casesyncservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
}
