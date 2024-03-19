package com.money.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequest(
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @NotBlank(message = "이메일은 공백일 수 없습니다.")
        @Schema(description = "이메일", example = "abc@naver.com")
        String email,
        @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
        @Schema(description = "비번", example = "1234")
        String password
) {

}

