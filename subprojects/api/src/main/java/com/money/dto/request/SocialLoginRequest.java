package com.money.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SocialLoginRequest(
        @NotBlank(message = "공백일 수 없습니다.")
        String token
) {

}
