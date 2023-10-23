package com.example.casesyncservice.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestTemplate;


@EnableCaching
@ComponentScan(basePackages = {"com.example.*"})
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableMongoRepositories(basePackages = "com.example.*")
public class AppConfig {

    @Bean("mongoTemplate")
    public MongoTemplate mongoTemplate(final MongoDatabaseFactory mongoDbFactory, @Nullable final MongoConverter mongoConverter) {
        return new MongoTemplate(mongoDbFactory, mongoConverter);
    }

    @Bean
    public MongoTransactionManager transactionManager(final MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public TransactionTemplate transactionTemplate(final MongoTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

}
