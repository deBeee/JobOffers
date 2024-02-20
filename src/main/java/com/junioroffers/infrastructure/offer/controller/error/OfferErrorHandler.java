package com.junioroffers.infrastructure.offer.controller.error;

import com.junioroffers.domain.offer.OfferNotFoundException;
import com.junioroffers.infrastructure.offer.controller.OfferRestController;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = OfferRestController.class)
@Log4j2
class OfferErrorHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    public ResponseEntity<OfferErrorHandlerResponse> handleOfferNotFoundException(OfferNotFoundException exception) {
        String message = exception.getMessage();
        log.error("OfferNotFoundException thrown with message: " + message);
        return new ResponseEntity<>(new OfferErrorHandlerResponse(message, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<OfferErrorHandlerResponse> handleDuplicateKeyException(DuplicateKeyException exception) {
        final String message = "Offer with given url already exists";
        log.error("DuplicateKeyException thrown with message: " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new OfferErrorHandlerResponse(message, HttpStatus.CONFLICT));
    }
}
