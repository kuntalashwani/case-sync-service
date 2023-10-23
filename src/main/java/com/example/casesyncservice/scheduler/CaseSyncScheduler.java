package com.example.casesyncservice.scheduler;

import com.example.casesyncservice.config.SchedulerConfig;
import com.example.casesyncservice.service.CaseSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "scheduler.schedulingEnabled", havingValue = "true")
public class CaseSyncScheduler {

    @Autowired
    private SchedulerConfig schedulerConfig;

    @Autowired
    private CaseSyncService caseSyncService;

    @Scheduled(cron = "${scheduler.time}")
    public void refreshMenuForTenants() {
        log.info("SchedulerEnabled flag is set to: {}", schedulerConfig.isSchedulingEnabled());
        if (schedulerConfig.isSchedulingEnabled()) {
            caseSyncService.syncCaseStatus();
        }
    }
}
