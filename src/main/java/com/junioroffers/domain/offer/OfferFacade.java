package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.dto.OfferRequestDto;
import com.junioroffers.domain.offer.dto.OfferDto;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.junioroffers.domain.offer.OfferMapper.*;

@AllArgsConstructor
public class OfferFacade {

    private final OfferRepository offerRepository;
    private final OfferService offerService;

    public List<OfferDto> findAllOffers(){
        List<Offer> offers = this.offerRepository.findAll();
        return mapListOfOfferToListOfOfferDto(offers);
    }

    public OfferDto findOfferById(String id){
        return this.offerRepository.findById(id)
                .map(OfferMapper::mapOfferToOfferDto)
                .orElseThrow(() -> new OfferNotFoundException("Offer with id=%s not found".formatted(id)));
    }

    public OfferDto saveOffer(OfferRequestDto offerRequestDto){
        Offer savedOffer = this.offerRepository.save(mapOfferRequestDtoToOffer(offerRequestDto));
        return mapOfferToOfferDto(savedOffer);
    }

    public List<OfferDto> saveAllOffers(List<OfferRequestDto> offerRequestDtos){
        List<Offer> savedOffers = this.offerRepository
                .saveAll(OfferMapper.mapListOfOfferRequestDtoToListOfOffer(offerRequestDtos));
        return mapListOfOfferToListOfOfferDto(savedOffers);
    }

    public List<OfferDto> fetchAllOffersAndSaveAllIfNotExists() {
        List<Offer> fetchedOffers = this.offerService.fetchAllOffersAndSaveAllIfNotExists();
        return mapListOfOfferToListOfOfferDto(fetchedOffers);
    }
}
