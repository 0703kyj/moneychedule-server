package com.money.dto.response.payment;

import lombok.Builder;

@Builder
public record TotalMonthDepositResponse(
        int month,
        Long totalDeposit
) {
    public static TotalMonthDepositResponse of(int month, Long totalDeposit) {
        return TotalMonthDepositResponse.builder()
                .month(month)
                .totalDeposit(totalDeposit)
                .build();
    }
}
