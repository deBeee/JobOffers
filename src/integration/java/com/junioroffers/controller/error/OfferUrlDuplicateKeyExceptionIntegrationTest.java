package com.junioroffers.controller.error;

import com.junioroffers.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OfferUrlDuplicateKeyExceptionIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_409_conflict_when_added_second_offer_with_same_offer_url() throws Exception {
        // step 1
        // given & when
        ResultActions performFirstTimePostOffer = mockMvc.perform(post("/offers")
                .content("""
                        {
                        "title": "someTitle",
                        "company": "someCompany",
                        "salary": "5 000 - 7 000 PLN",
                        "url": "https://newoffers.pl/offer/1234"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        // then
        performFirstTimePostOffer.andExpect(status().isCreated());

        // step 2
        // given && when
        ResultActions performSecondTimePostOffer = mockMvc.perform(post("/offers")
                .content("""
                        {
                        "title": "someTitle",
                        "company": "someCompany",
                        "salary": "5 000 - 7 000 PLN",
                        "url": "https://newoffers.pl/offer/1234"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        // then
        performSecondTimePostOffer.andExpect(status().isConflict())
                .andExpect(content().json("""
                        {
                        "message": "Offer with given url already exists",
                        "status": "CONFLICT"
                        }
                        """.trim()));
    }
}
