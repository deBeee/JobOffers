package com.junioroffers.infrastructure.offer.controller;

import com.junioroffers.domain.offer.OfferFacade;
import com.junioroffers.domain.offer.dto.OfferDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Log4j2
public class OfferController {

    private final OfferFacade offerFacade;
    @GetMapping(path = "/offers")
    public ResponseEntity<AllOffersResponseDto> getOffers(){
        List<OfferDto> allOffers = this.offerFacade.findAllOffers();
        return ResponseEntity.ok(new AllOffersResponseDto(allOffers));
    }
}
