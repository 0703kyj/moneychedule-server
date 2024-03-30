package com.money.dto.response.payment;

import lombok.Builder;

@Builder
public record TotalMonthPaymentResponse(
        int year,
        int month,
        Long totalAmount
) {
    public static TotalMonthPaymentResponse of(int year, int month, Long totalAmount) {
        return TotalMonthPaymentResponse.builder()
                .year(year)
                .month(month)
                .totalAmount(totalAmount)
                .build();
    }
}
