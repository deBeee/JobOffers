package com.junioroffers.infrastructure.offer.controller.error;

import org.springframework.http.HttpStatus;

public record OfferErrorHandlerResponse(String message, HttpStatus status) {
}
