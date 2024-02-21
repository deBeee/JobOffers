package com.junioroffers.features;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.junioroffers.BaseIntegrationTest;
import com.junioroffers.SampleJobOfferResponse;
import com.junioroffers.domain.offer.OfferFacade;
import com.junioroffers.domain.offer.dto.OfferDto;
import com.junioroffers.infrastructure.offer.controller.AllOffersResponseDto;
import com.junioroffers.infrastructure.offer.scheduler.HttpOffersScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TypicalScenarioUserWantToSeeOffersIntegrationTest extends BaseIntegrationTest implements SampleJobOfferResponse {

    @Autowired
    HttpOffersScheduler httpOffersScheduler;

    @Test
    public void user_want_to_see_offers_but_have_to_be_logged_in_and_external_server_should_have_some_offers() throws Exception {
        //step 1: there are no offers in external HTTP server
        //given & when & then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithZeroOffersJson())));

        //step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        //given & when
        List<OfferDto> newOffers = httpOffersScheduler.fetchAllOffersAndSaveAllIfNotExists();//explicit execution of scheduler (so mocked server can get up before http client sends request) and respond to that request, scheduler is tested separately
        //then
        assertThat(newOffers).isEmpty();

        //step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        //step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
        //step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
        //step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        //step 7: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
        //given
        String getOffersUrl = "/offers";
        //when
        ResultActions performGetAndExpectNoOffers = mockMvc.perform(get(getOffersUrl));
        //then
        MvcResult mvcResult = performGetAndExpectNoOffers.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        AllOffersResponseDto allOffersResponseDto = objectMapper.readValue(json, AllOffersResponseDto.class);
        List<OfferDto> offers = allOffersResponseDto.offers();
        assertThat(offers).isEmpty();


        //step 8: there are 2 new offers in external HTTP server
        // given & when & then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithTwoOffersJson())));


        //step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
        //given & when
        List<OfferDto> twoNewOffers = httpOffersScheduler.fetchAllOffersAndSaveAllIfNotExists();
        //then
        assertThat(twoNewOffers).hasSize(2);


        //step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000
        // given & when
        ResultActions performGetAndExpectTwoOffers = mockMvc.perform(get(getOffersUrl));
        //then
        MvcResult performGetAndExpectTwoOffersMvcResult = performGetAndExpectTwoOffers.andExpect(status().isOk()).andReturn();
        String jsonWithTwoOffers = performGetAndExpectTwoOffersMvcResult.getResponse().getContentAsString();
        AllOffersResponseDto twoOffersResponseDto = objectMapper.readValue(jsonWithTwoOffers, AllOffersResponseDto.class);
        List<OfferDto> twoOffers = twoOffersResponseDto.offers();
        assertThat(twoOffers).hasSize(2);

        OfferDto expectedFirstOffer = twoNewOffers.get(0);
        OfferDto expectedSecondOffer = twoNewOffers.get(1);
        assertThat(twoOffers).containsExactlyInAnyOrder(
                new OfferDto(expectedFirstOffer.id(), expectedFirstOffer.title(), expectedFirstOffer.company(), expectedFirstOffer.salary(), expectedFirstOffer.url()),
                new OfferDto(expectedSecondOffer.id(), expectedSecondOffer.title(), expectedSecondOffer.company(), expectedSecondOffer.salary(), expectedSecondOffer.url())
        );


        //step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
        //given
        String getOfferByNoExistingIdUrl = "/offers/9999";
        //when
        ResultActions performGetOfferByIdEndpoint = mockMvc.perform(get(getOfferByNoExistingIdUrl));
        //then
        performGetOfferByIdEndpoint.andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                        "message": "Offer with id=9999 not found",
                        "status": "NOT_FOUND"
                        }
                        """.trim()));


        //step 12: user made GET /offers/1000 and system returned OK(200) with offer
        //given
        String offerIdAddedToDatabase = expectedFirstOffer.id();
        String getOfferByExistingIdUrl = "/offers" + "/" + offerIdAddedToDatabase;
        //when
        ResultActions performGetAndExpectExistingOffer = mockMvc.perform(get(getOfferByExistingIdUrl));
        //then
        String singleOfferJson = performGetAndExpectExistingOffer.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        OfferDto singleOfferDto = objectMapper.readValue(singleOfferJson, OfferDto.class);
        assertThat(singleOfferDto).isEqualTo(expectedFirstOffer);


        //step 13: there are 2 new offers in external HTTP server
        // given & when & then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithFourOffersJson())));


        //step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
        //given & when
        List<OfferDto> nextTwoNewOffers = httpOffersScheduler.fetchAllOffersAndSaveAllIfNotExists();
        //then
        assertThat(nextTwoNewOffers).hasSize(2);


        //step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000
        ResultActions performGetAndExpectFourOffers = mockMvc.perform(get(getOffersUrl));
        //then
        MvcResult performGetAndExpectFourOffersMvcResult = performGetAndExpectFourOffers.andExpect(status().isOk()).andReturn();
        String jsonWithFourOffers = performGetAndExpectFourOffersMvcResult.getResponse().getContentAsString();
        AllOffersResponseDto FourOffersResponseDto = objectMapper.readValue(jsonWithFourOffers, AllOffersResponseDto.class);
        List<OfferDto> fourOffers = FourOffersResponseDto.offers();
        assertThat(fourOffers).hasSize(4);

        OfferDto expectedThirdOffer = nextTwoNewOffers.get(0);
        OfferDto expectedFourthOffer = nextTwoNewOffers.get(1);
        assertThat(fourOffers).contains(
                new OfferDto(expectedThirdOffer.id(), expectedThirdOffer.title(), expectedThirdOffer.company(), expectedThirdOffer.salary(), expectedThirdOffer.url()),
                new OfferDto(expectedFourthOffer.id(), expectedFourthOffer.title(), expectedFourthOffer.company(), expectedFourthOffer.salary(), expectedFourthOffer.url())
        );


        //step 16: user made POST /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and offer as body and system returned CREATED(201) with saved offer
        // given
        // when
        ResultActions performPostOffersWithOneOffer = mockMvc.perform(post("/offers")
                .content("""
                        {
                        "title": "someTitle",
                        "company": "someCompany",
                        "salary": "7 000 - 9 000 PLN",
                        "url": "https://newoffers.pl/offer/1234"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        String createdOfferJson = performPostOffersWithOneOffer.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OfferDto parsedCreatedOfferJson = objectMapper.readValue(createdOfferJson, OfferDto.class);
        String id = parsedCreatedOfferJson.id();
        assertAll(
                () -> assertThat(parsedCreatedOfferJson.title()).isEqualTo("someTitle"),
                () -> assertThat(parsedCreatedOfferJson.company()).isEqualTo("someCompany"),
                () -> assertThat(parsedCreatedOfferJson.salary()).isEqualTo("7 000 - 9 000 PLN"),
                () -> assertThat(parsedCreatedOfferJson.url()).isEqualTo("https://newoffers.pl/offer/1234"),
                () -> assertThat(id).isNotNull()
        );


        //step 17: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 5 offers
        // given & when
        ResultActions peformGetOffers = mockMvc.perform(get("/offers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        String oneOfferJson = peformGetOffers.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        AllOffersResponseDto parsedJsonWithOneOffer = objectMapper.readValue(oneOfferJson, AllOffersResponseDto.class);
        assertThat(parsedJsonWithOneOffer.offers()).hasSize(5);
        assertThat(parsedJsonWithOneOffer.offers().stream().map(OfferDto::id)).contains(id);
    }
}
