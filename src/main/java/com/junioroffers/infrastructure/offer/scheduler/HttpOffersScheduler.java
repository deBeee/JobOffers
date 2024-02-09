package com.junioroffers.infrastructure.offer.scheduler;

import com.junioroffers.domain.offer.OfferFacade;
import com.junioroffers.domain.offer.dto.OfferDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class HttpOffersScheduler {

    private final OfferFacade offerFacade;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelayString = "${offer.scheduler.request.delay}")
    public void fetchAllOffersAndSaveAllIfNotExists() {
        log.info("Started offers fetching {}", dateFormat.format(new Date()));
        final List<OfferDto> savedOffers = offerFacade.fetchAllOffersAndSaveAllIfNotExists();
        log.info("Added new {} offers", savedOffers.size());
        log.info("Stopped offers fetching {}", dateFormat.format(new Date()));
    }
}
