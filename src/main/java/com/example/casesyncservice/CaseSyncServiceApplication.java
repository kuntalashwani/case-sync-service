package com.example.casesyncservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@Slf4j
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.*"})
public class CaseSyncServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaseSyncServiceApplication.class, args);
		log.info("Case Sync Service started ....");
	}

}
