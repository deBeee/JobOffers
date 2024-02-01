package com.junioroffers.domain.offer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOfferRepositoryTestImpl implements OfferRepository{
    Map<String, Offer> offerDatabase = new ConcurrentHashMap<>();
    @Override
    public List<Offer> findAll() {
        return this.offerDatabase.values().stream().toList();
    }

    @Override
    public Optional<Offer> findById(String id) {
        return Optional.ofNullable(offerDatabase.get(id));
    }

    @Override
    public Offer save(Offer entity) {
        if (offerDatabase.values().stream().anyMatch(offer -> offer.url().equals(entity.url()))) {
            throw new OfferDuplicateException(entity.url());
        }
        Offer offerToSave = new Offer(
                UUID.randomUUID().toString(),
                entity.title(),
                entity.company(),
                entity.salary(),
                entity.url()
        );
        this.offerDatabase.put(offerToSave.id(), offerToSave);
        return offerToSave;
    }

    @Override
    public List<Offer> saveAll(List<Offer> offersToSave) {
        offersToSave.forEach(this::save);
        return offersToSave;
    }

    @Override
    public boolean existsByOfferUrl(String url) {
        return this.offerDatabase.values().stream().anyMatch(offer -> offer.url().equals(url));
    }
}
