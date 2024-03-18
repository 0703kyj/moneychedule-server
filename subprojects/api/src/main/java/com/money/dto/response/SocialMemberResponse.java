package com.money.dto.response;

import lombok.Builder;

@Builder
public record SocialMemberResponse(
        String email,
        String platformId
) {
}
