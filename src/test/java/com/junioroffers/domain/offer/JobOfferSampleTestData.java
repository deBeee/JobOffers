package com.junioroffers.domain.offer;

import com.junioroffers.domain.offer.dto.OfferRequestDto;

class JobOfferSampleTestData {

    static final JobOfferResponse BLUESOFT_JOB_OFFER_RESPONSE = new JobOfferResponse(
            "Junior Java Developer",
            "BlueSoft Sp. z o.o.",
            "7 000 – 9 000 PLN",
            "https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre");
    static final JobOfferResponse EFIGENCE_JOB_OFFER_RESPONSE = new JobOfferResponse(
            "Java (CMS) Developer",
            "Efigence SA",
            "16 000 – 18 000 PLN",
            "https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh");
    static final JobOfferResponse SOLLERS_JOB_OFFER_RESPONSE = new JobOfferResponse(
            "Junior Java Developer",
            "Sollers Consulting",
            "7 500 – 11 500 PLN",
            "https://nofluffjobs.com/pl/job/junior-java-developer-sollers-consulting-warszawa-s6et1ucc");
    static final JobOfferResponse VERTABELO_JOB_OFFER_RESPONSE = new JobOfferResponse(
            "Junior Full Stack Developer",
            "Vertabelo S.A.",
            "7 000 – 9 000 PLN",
            "https://nofluffjobs.com/pl/job/junior-full-stack-developer-vertabelo-remote-k7m9xpnm");
    static final JobOfferResponse NIXTECH_JOB_OFFER_RESPONSE = new JobOfferResponse(
            "Junior Java Developer",
            "NIX Tech Kft.",
            "6 169 – 12 339 PLN",
            "https://nofluffjobs.com/pl/job/junior-java-developer-nix-tech-kft-budapest-d1wuebdj");
    static final JobOfferResponse BNYMELLON_JOB_OFFER_RESPONSE = new JobOfferResponse(
            "2023 Technology Program BNY Mellon",
            "BNY Mellon",
            "5 833 – 7 500 PLN",
            "https://nofluffjobs.com/pl/job/2023-technology-program-bny-mellon-bny-mellon-remote-ezutwncf");
    static final OfferRequestDto BLUESOFT_OFFER_REQUEST_DTO = mapJobOfferResponseToOfferRequestDto(BLUESOFT_JOB_OFFER_RESPONSE);
    static final OfferRequestDto EFIGENCE_OFFER_REQUEST_DTO = mapJobOfferResponseToOfferRequestDto(EFIGENCE_JOB_OFFER_RESPONSE);
    static final OfferRequestDto SOLLERS_OFFER_REQUEST_DTO = mapJobOfferResponseToOfferRequestDto(SOLLERS_JOB_OFFER_RESPONSE);
    static final OfferRequestDto VERTABELO_OFFER_REQUEST_DTO = mapJobOfferResponseToOfferRequestDto(VERTABELO_JOB_OFFER_RESPONSE);
    static final OfferRequestDto NIXTECH_OFFER_REQUEST_DTO = mapJobOfferResponseToOfferRequestDto(NIXTECH_JOB_OFFER_RESPONSE);
    static final OfferRequestDto BNYMELLON_OFFER_REQUEST_DTO = mapJobOfferResponseToOfferRequestDto(BNYMELLON_JOB_OFFER_RESPONSE);

    static private OfferRequestDto mapJobOfferResponseToOfferRequestDto(JobOfferResponse jobOfferResponse) {
        return new OfferRequestDto(
                jobOfferResponse.title(),
                jobOfferResponse.company(),
                jobOfferResponse.salary(),
                jobOfferResponse.url()
        );
    }
}
