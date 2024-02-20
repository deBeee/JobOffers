package com.junioroffers.domain.offer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OfferRequestDto(
        @NotNull(message = "{title.not.null}")
        @NotEmpty(message = "{title.not.empty}")
        String title,
        @NotNull(message = "{company.not.null}")
        @NotEmpty(message = "{company.not.empty}")
        String company,
        @NotNull(message = "{salary.not.null}")
        @NotEmpty(message = "{salary.not.empty}")
        String salary,
        @NotNull(message = "{url.not.null}")
        @NotEmpty(message = "{url.not.empty}")
        String url) {
}
