package com.junioroffers.domain.offer;

import java.util.List;

public class InMemoryFetcherTestImpl implements OfferFetchable {

    List<JobOfferResponse> listOfOffers;

    InMemoryFetcherTestImpl(List<JobOfferResponse> listOfOffers) {
        this.listOfOffers = listOfOffers;
    }

    @Override
    public List<JobOfferResponse> fetchOffers() {
        return listOfOffers;
    }
}
