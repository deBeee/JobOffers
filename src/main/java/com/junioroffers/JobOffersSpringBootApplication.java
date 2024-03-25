package com.junioroffers;

import com.junioroffers.infrastructure.offer.http.OfferHttpClientConfigurationProperties;
import com.junioroffers.infrastructure.security.jwt.JwtConfigurationProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication(exclude = {MongoReactiveAutoConfiguration.class})
@EnableConfigurationProperties(value = {OfferHttpClientConfigurationProperties.class, JwtConfigurationProperties.class})
@EnableMongoRepositories
//@EnableRedisRepositories
@Log4j2
public class JobOffersSpringBootApplication {

    public static void main(String[] args) {
        log.info("<-------------- Running application -------------->");
        SpringApplication.run(JobOffersSpringBootApplication.class, args);
    }

}