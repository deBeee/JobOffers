package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.dto.OfferDto;
import com.junioroffers.domain.offer.dto.OfferRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.stream.Stream;

import static com.junioroffers.domain.offer.JobOfferSampleTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

class OfferFacadeTest {

    @Test
    public void should_fetch_all_offers_and_save_all_offers_if_database_is_empty() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        assertThat(offerFacade.findAllOffers()).isEmpty();
        //when
        List<OfferDto> allOffers = offerFacade.fetchAllOffersAndSaveAllIfNotExists();
        //then
        assertThat(allOffers).hasSize(6);
    }

    @Test
    public void should_fetch_all_offers_and_save_only_2_offers_that_differ_in_url_to_avoid_duplications() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of(
                BNYMELLON_JOB_OFFER_RESPONSE,
                SOLLERS_JOB_OFFER_RESPONSE,
                VERTABELO_JOB_OFFER_RESPONSE,
                NIXTECH_JOB_OFFER_RESPONSE,
                BLUESOFT_JOB_OFFER_RESPONSE,
                EFIGENCE_JOB_OFFER_RESPONSE
        )).offerFacadeForTests();

        offerFacade.saveAllOffers(List.of(
                BNYMELLON_OFFER_REQUEST_DTO,
                SOLLERS_OFFER_REQUEST_DTO,
                VERTABELO_OFFER_REQUEST_DTO,
                NIXTECH_OFFER_REQUEST_DTO));
        Assertions.assertThat(offerFacade.findAllOffers()).hasSize(4);

        //when
        List<OfferDto> savedOffers = offerFacade.fetchAllOffersAndSaveAllIfNotExists();
        Stream<String> offersUrls = savedOffers.stream().map(OfferDto::url);

        //then
        assertThat(offersUrls).containsOnly(
                "https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre",
                "https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh"
        );
    }

    @Test
    public void should_save_all_offers_if_database_is_empty() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        //when
        offerFacade.saveAllOffers(List.of(
                BNYMELLON_OFFER_REQUEST_DTO,
                SOLLERS_OFFER_REQUEST_DTO,
                VERTABELO_OFFER_REQUEST_DTO,
                NIXTECH_OFFER_REQUEST_DTO
        ));

        //then
        assertThat(offerFacade.findAllOffers()).hasSize(4);
    }

    @Test
    public void should_retrieve_offer_if_offer_exists() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        OfferDto savedOffer = offerFacade.saveOffer(SOLLERS_OFFER_REQUEST_DTO);

        //when
        OfferDto foundOffer = offerFacade.findOfferById(savedOffer.id());

        //then
        assertThat(savedOffer).isEqualTo(foundOffer);
    }

    @Test
    public void should_throw_not_found_exception_if_offer_does_not_exist(){
        // given
        String sampleId = "1";
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        assertThat(offerFacade.findAllOffers()).isEmpty();

        //then
        assertThatThrownBy(() -> offerFacade.findOfferById(sampleId))
                .isInstanceOf(OfferNotFoundException.class)
                .hasMessage("Offer with id=%s not found".formatted(sampleId));
    }

    @Test
    public void should_throw_duplicate_key_exception_when_saving_offer_that_already_exists() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        OfferDto savedOffer = offerFacade.saveOffer(SOLLERS_OFFER_REQUEST_DTO);

        //when
        Throwable thrown = catchThrowable(() -> offerFacade.saveOffer(SOLLERS_OFFER_REQUEST_DTO));

        //then
        assertThat(thrown)
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessage("Offer with offerUrl '%s' already exists".formatted(SOLLERS_OFFER_REQUEST_DTO.url()));
    }
}