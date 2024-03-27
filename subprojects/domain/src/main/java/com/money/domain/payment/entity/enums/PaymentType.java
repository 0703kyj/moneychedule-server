package com.money.domain.payment.entity.enums;

import com.money.domain.payment.exception.NotFoundDepositTypeException;
import com.money.domain.payment.exception.NotFoundPaymentTypeException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentType {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw");

    private final String value;

    public static PaymentType fromString(String value) {
        for (PaymentType payment : PaymentType.values()) {
            if (payment.value.equalsIgnoreCase(value)) {
                return payment;
            }
        }
        throw new NotFoundPaymentTypeException();
    }
}
