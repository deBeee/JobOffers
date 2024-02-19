package com.junioroffers.infrastructure.offer.controller.error;

import com.junioroffers.domain.offer.OfferNotFoundException;
import com.junioroffers.infrastructure.offer.controller.OfferRestController;
import com.junioroffers.infrastructure.offer.controller.error.dto.OfferNotFoundResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = OfferRestController.class)
@Log4j2
public class OfferErrorHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    public ResponseEntity<OfferNotFoundResponseDto> handleOfferNotFoundException(OfferNotFoundException exception) {
        String message = exception.getMessage();
        log.error("OfferNotFoundException thrown with message: " + message);
        return new ResponseEntity<>(new OfferNotFoundResponseDto(message, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}
