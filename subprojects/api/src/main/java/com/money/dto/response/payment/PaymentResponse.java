package com.money.dto.response.payment;

public record PaymentResponse(
        Long paymentId
) {
    public static PaymentResponse from(Long paymentId) {
        return new PaymentResponse(paymentId);
    }
}
