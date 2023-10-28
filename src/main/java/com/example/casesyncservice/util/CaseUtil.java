package com.example.casesyncservice.util;

import com.example.casesyncservice.model.Cases;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class CaseUtil {

    public static Cases readJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClassPathResource resource = new ClassPathResource("cases-data.json");
            return objectMapper.readValue(resource.getInputStream(), Cases.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
