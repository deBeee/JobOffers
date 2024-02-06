package com.junioroffers.domain.offer;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
class OfferService {

    private final OfferFetchable offerFetcher;
    private final OfferRepository offerRepository;

    List<Offer> fetchAllOffersAndSaveAllIfNotExists() {
        List<Offer> fetchedOffers = fetchOffers();
        List<Offer> offersToSave = filterValidAndNotExistingInDatabaseOffers(fetchedOffers);
        try {
            return offerRepository.saveAll(offersToSave);
        } catch (OfferDuplicateException duplicateKeyException) {
            throw new OfferSavingException(duplicateKeyException.getMessage(), fetchedOffers);
        }
    }

    private List<Offer> filterValidAndNotExistingInDatabaseOffers(List<Offer> jobOffers) {
        return jobOffers.stream()
                .filter(offerDto -> !offerDto.url().isEmpty())
                .filter(offerDto -> !offerRepository.existsByOfferUrl(offerDto.url()))
                .toList();
    }

    private List<Offer> fetchOffers() {
        return offerFetcher.fetchOffers()
                .stream()
                .map(OfferMapper::mapJobOfferResponseToOffer)
                .toList();
    }
}
