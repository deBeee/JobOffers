package com.junioroffers.domain.offer;

import java.util.List;

public interface OfferFetchable {
    List<JobOfferResponse> fetchOffers();
}
