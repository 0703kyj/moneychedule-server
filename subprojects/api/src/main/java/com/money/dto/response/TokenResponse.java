package com.money.dto.response;

public record TokenResponse(
        Long memberId,
        String token,
        Boolean isRegistered
) {

    public static TokenResponse of(final Long memberId, final String token,
            final Boolean isRegistered) {
        return new TokenResponse(memberId, token, isRegistered);
    }
}
