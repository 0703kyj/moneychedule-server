package com.money.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Oauth2Request(
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @NotBlank(message = "이메일은 공백일 수 없습니다.")
        @Schema(description = "이메일", example = "abc@naver.com")
        String email,
        String platformId
) {

}
