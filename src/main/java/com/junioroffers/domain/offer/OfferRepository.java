package com.junioroffers.domain.offer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
interface OfferRepository extends MongoRepository<Offer, String> {
    boolean existsByUrl(String url);
}
