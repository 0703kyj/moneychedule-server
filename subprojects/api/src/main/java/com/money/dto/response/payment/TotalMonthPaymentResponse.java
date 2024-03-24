package com.money.dto.response.payment;

import lombok.Builder;

@Builder
public record TotalMonthPaymentResponse(
        int month,
        Long totalAmount
) {
    public static TotalMonthPaymentResponse of(int month, Long totalAmount) {
        return TotalMonthPaymentResponse.builder()
                .month(month)
                .totalAmount(totalAmount)
                .build();
    }
}
