package com.junioroffers.infrastructure.offer.http;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "offer.http.client.config")
public record OfferHttpClientConfigurationProperties(
        long connectionTimeout,
        long readTimeout,
        String uri,
        int port,
        String service) {
}
