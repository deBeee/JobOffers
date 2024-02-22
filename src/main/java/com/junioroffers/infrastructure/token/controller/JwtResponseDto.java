package com.junioroffers.infrastructure.token.controller;

import lombok.Builder;

@Builder
public record JwtResponseDto(
        String username,
        String token
) {
}
