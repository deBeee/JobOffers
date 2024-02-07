package com.junioroffers.scheduler;

import com.junioroffers.BaseIntegrationTest;
import com.junioroffers.JobOffersSpringBootApplication;
import com.junioroffers.domain.offer.OfferFetchable;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest(classes = JobOffersSpringBootApplication.class, properties = "offer.scheduler.config.enabled=true")
public class ExternalServerFetcherSchedulerTest extends BaseIntegrationTest {

    @SpyBean
    OfferFetchable externalServerFetcher;

    @Test
    public void should_run_http_client_offers_fetching_exactly_given_times() {
        await().
                atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(externalServerFetcher, times(1)).fetchOffers());
    }
}
