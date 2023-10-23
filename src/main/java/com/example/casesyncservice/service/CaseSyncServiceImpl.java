package com.example.casesyncservice.service;

import com.example.casesyncservice.client.CaseServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CaseSyncServiceImpl implements CaseSyncService {

    @Autowired
    private CaseServiceClient caseServiceClient;

    @Override
    public void syncCaseStatus() {
        log.info("Calling Case Service Client GET API to fetch all cases details");
    }
}
