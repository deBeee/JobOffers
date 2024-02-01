package com.junioroffers.domain.offer;

import java.util.List;

import static com.junioroffers.domain.offer.JobOfferSampleTestData.*;

public class OfferFacadeTestConfiguration {

    private final InMemoryFetcherTestImpl inMemoryFetcherTest;
    private final InMemoryOfferRepositoryTestImpl offerRepository;

    OfferFacadeTestConfiguration() {
        this.inMemoryFetcherTest = new InMemoryFetcherTestImpl(
                List.of(
                        SOLLERS_JOB_OFFER_RESPONSE,
                        BLUESOFT_JOB_OFFER_RESPONSE,
                        BNYMELLON_JOB_OFFER_RESPONSE,
                        EFIGENCE_JOB_OFFER_RESPONSE,
                        NIXTECH_JOB_OFFER_RESPONSE,
                        VERTABELO_JOB_OFFER_RESPONSE
                )
        );
        this.offerRepository = new InMemoryOfferRepositoryTestImpl();
    }

    OfferFacadeTestConfiguration(List<JobOfferResponse> remoteClientOffers) {
        this.inMemoryFetcherTest = new InMemoryFetcherTestImpl(remoteClientOffers);
        this.offerRepository = new InMemoryOfferRepositoryTestImpl();
    }

    OfferFacade offerFacadeForTests() {
        return new OfferFacade(offerRepository, new OfferService(inMemoryFetcherTest, offerRepository));
    }
}
