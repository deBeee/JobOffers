package com.junioroffers.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferDto(String id, String title, String company, String salary, String url) {
}
