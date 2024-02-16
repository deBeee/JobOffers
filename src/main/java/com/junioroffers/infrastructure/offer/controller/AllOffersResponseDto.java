package com.junioroffers.infrastructure.offer.controller;

import com.junioroffers.domain.offer.dto.OfferDto;

import java.util.List;

public record AllOffersResponseDto(List<OfferDto> offers) {
}
