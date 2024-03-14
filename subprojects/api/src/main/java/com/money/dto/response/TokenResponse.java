package com.money.dto.response;

public record TokenResponse(
        Long memberId,
        String token
) {
    public static TokenResponse of(final Long memberId, final String token) {
        return new TokenResponse(memberId, token);
    }
}
