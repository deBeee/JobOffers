package com.junioroffers.infrastructure.offer.controller;

import com.junioroffers.domain.offer.OfferFacade;
import com.junioroffers.domain.offer.dto.OfferDto;
import com.junioroffers.domain.offer.dto.OfferRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/offers")
public class OfferRestController {

    private final OfferFacade offerFacade;
    @GetMapping
    public ResponseEntity<AllOffersResponseDto> getOffers(){
        List<OfferDto> allOffers = this.offerFacade.findAllOffers();
        return ResponseEntity.ok(new AllOffersResponseDto(allOffers));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OfferDto> getOfferById(@PathVariable String id){
        OfferDto offer = this.offerFacade.findOfferById(id);
        return ResponseEntity.ok(offer);
    }

    @PostMapping
    public ResponseEntity<OfferDto> addOffer(@RequestBody @Valid OfferRequestDto offerRequestDto){
        OfferDto savedOffer = this.offerFacade.saveOffer(offerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOffer);
    }
}
