package com.junioroffers.infrastructure.offer.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "offer.scheduler.config.enabled")
class HttpOfferSchedulerConfiguration {
}
