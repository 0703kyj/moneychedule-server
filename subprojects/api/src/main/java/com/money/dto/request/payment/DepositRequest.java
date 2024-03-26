package com.money.dto.request.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

public record DepositRequest(
        @Schema(description = "세부 내용", example = "메모")
        String memo,
        @Min(value = 0,message = "금액은 0 이하일 수 없습니다.")
        @Schema(description = "금액", example = "1000")
        Long amount,
        @Schema(description = "입금 종류", example = "용돈")
        String depositType
) {

}
