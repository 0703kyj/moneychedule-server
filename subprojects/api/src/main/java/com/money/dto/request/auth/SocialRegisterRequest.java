package com.money.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record SocialRegisterRequest(
        String platformId,
        String name,
        @Schema(example = "010-1234-5678")
        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호 양식에 맞지 않습니다.")
        String phoneNumber,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birth
) {

}
