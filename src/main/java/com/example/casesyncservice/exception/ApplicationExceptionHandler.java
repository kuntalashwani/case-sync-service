package com.example.casesyncservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleExceptions(final Exception ex) {
        log.error("Something went wrong", ExceptionUtils.getRootCause(ex));
        return new ResponseEntity<>(new ExceptionResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of("Something went wrong")), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
