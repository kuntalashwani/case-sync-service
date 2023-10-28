package com.example.casesyncservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class CaseSyncException extends ResponseStatusException {

    public CaseSyncException(final HttpStatus status) {
        super(status);
        log.error("Status: {}", status);
    }

    public CaseSyncException(final HttpStatus status, final String reason) {
        super(status, reason);
        log.error("Status : {}, Reason: {}", status, reason);
    }
}
