package com.money.domain.payment.exception;


import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class InvalidPaymentException extends MainException {

    public InvalidPaymentException() {
        super(ErrorCode.INVALID_PAYMENT);
    }
}
