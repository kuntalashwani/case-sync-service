package com.example.casesyncservice.service;

import com.example.casesyncservice.client.CaseServiceClient;
import com.example.casesyncservice.config.SyncConfig;
import com.example.casesyncservice.model.Cases;
import com.example.casesyncservice.model.StatusData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static com.example.casesyncservice.util.AppConstants.*;

@Slf4j
@Service
public class CaseSyncServiceImpl implements CaseSyncService {

    @Autowired
    private CaseServiceClient caseServiceClient;

    @Autowired
    private SyncConfig syncConfig;

    @Override
    public void syncCaseStatus() {
        log.info("Calling Case Service Client GET API to fetch all cases details");
        Cases caseList = caseServiceClient.fetchCasesFromExternalService(buildFilterExpression());
        if(caseList!=null) {
            markAndSyncCaseStatus(caseList);
        } else {
            log.error("No cases filtered for syncing and marking Closed status.");
        }
    }

    private String buildFilterExpression() {
        LocalDate currentDate = LocalDate.now();
        String currentFormattedDate = currentDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        LocalDate dateMinus30Days = currentDate.minus(Period.ofDays(syncConfig.getNoOfDays()));
        String tillLastDate = dateMinus30Days.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        return OPENING_BRACES + ADMIN_DATA + UPDATED_ON + BETWEEN + currentFormattedDate + SPACE  + tillLastDate
                + CLOSING_BRACES + AND + OPENING_BRACES + STATUS + EQ + syncConfig.getFromStatus() + CLOSING_BRACES;
    }

    private void markAndSyncCaseStatus(final Cases cases) {
        cases.getValue().forEach(caseItem -> {
            if(caseItem.getCaseType().equals(syncConfig.getCaseType())
                    && caseItem.getStatus().equals(syncConfig.getFromStatus())) {
                StatusData statusData = new StatusData(syncConfig.getToStatus(), CLOSED);
                caseServiceClient.updateCaseStatus(caseItem.getId(), statusData);
            }
        });
    }
}
