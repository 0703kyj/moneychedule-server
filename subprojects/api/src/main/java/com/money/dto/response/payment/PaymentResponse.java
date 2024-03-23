package com.money.dto.response.payment;

import com.money.domain.payment.entity.Payment;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PaymentResponse(
        Long paymentId,
        LocalDateTime paymentDate
) {
    public static PaymentResponse from(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .paymentDate(payment.getPaymentDate())
                .build();
    }
}
