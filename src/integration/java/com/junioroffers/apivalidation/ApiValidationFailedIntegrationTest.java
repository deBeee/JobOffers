package com.junioroffers.apivalidation;

import com.junioroffers.BaseIntegrationTest;
import com.junioroffers.infrastructure.apivalidation.ApiValidationErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_400_bad_request_and_proper_validation_messages_when_invalid_data_sent_to_post_offers_endpoint() throws Exception {
        // given & when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content("""
                        {
                        "title": "",
                        "company": "",
                        "salary": ""
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        // then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(result.messages()).containsExactlyInAnyOrder(
                "title must not be empty",
                "company must not be empty",
                "salary must not be empty",
                "url must not be null",
                "url must not be empty");
    }
}
