package com.example.casesyncservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseDTO {
    private int errorCode;
    private List<String> errorMessage;
}
