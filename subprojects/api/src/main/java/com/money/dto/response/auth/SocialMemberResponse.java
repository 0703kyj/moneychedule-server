package com.money.dto.response.auth;

import lombok.Builder;

@Builder
public record SocialMemberResponse(
        String email,
        String platformId
) {
}
