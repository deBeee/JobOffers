package com.junioroffers.domain.offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {
    List<Offer> findAll();

    Optional<Offer> findById(String id);

    Offer save(Offer offerToSave);

    List<Offer> saveAll(List<Offer> offersToSave);

    boolean existsByOfferUrl(String url);
}
