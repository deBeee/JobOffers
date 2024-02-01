package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.dto.OfferDto;
import com.junioroffers.domain.offer.dto.OfferRequestDto;

import java.util.List;

public interface OfferMapper {
    static List<OfferDto> mapListOfOfferToListOfOfferDto(List<Offer> offers) {
        return offers.stream()
                .map(OfferMapper::mapOfferToOfferDto)
                .toList();
    }

    static OfferDto mapOfferToOfferDto(Offer offer) {
        return new OfferDto(offer.id(), offer.title(), offer.company(), offer.salary(), offer.url());
    }

    static Offer mapOfferRequestDtoToOffer(OfferRequestDto offerRequestDto) {
        return Offer.builder()
                .title(offerRequestDto.title())
                .company(offerRequestDto.company())
                .salary(offerRequestDto.salary())
                .url(offerRequestDto.url())
                .build();
    }

    static Offer mapJobOfferResponseToOffer(JobOfferResponse jobOfferResponse) {
        return Offer.builder()
                .title(jobOfferResponse.title())
                .company(jobOfferResponse.company())
                .salary(jobOfferResponse.salary())
                .url(jobOfferResponse.url())
                .build();
    }

    static List<Offer> mapListOfOfferRequestDtoToListOfOffer(List<OfferRequestDto> offerRequestDtos) {
        return offerRequestDtos.stream()
                .map(OfferMapper::mapOfferRequestDtoToOffer)
                .toList();
    }
}
