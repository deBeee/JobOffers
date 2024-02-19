package com.junioroffers.infrastructure.offer.controller.error.dto;

import org.springframework.http.HttpStatus;

public record OfferNotFoundResponseDto(String message, HttpStatus status) {
}
