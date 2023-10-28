package com.example.casesyncservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sync")
public class SyncConfig {
    private String caseType;
    private String rootCategory;
    private String fromStatus;
    private String toStatus;
    private int noOfDays;
}
