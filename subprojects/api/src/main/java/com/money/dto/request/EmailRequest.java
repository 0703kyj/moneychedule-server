package com.money.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequest(
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @NotBlank(message = "이메일은 공백일 수 없습니다.")
        String email,
        @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
        String password
) {

}

