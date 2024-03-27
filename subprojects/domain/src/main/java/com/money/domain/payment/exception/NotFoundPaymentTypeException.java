package com.money.domain.payment.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class NotFoundPaymentTypeException extends MainException {

    public NotFoundPaymentTypeException() {
        super(ErrorCode.NOT_FOUND_PAYMENT_TYPE);
    }
}
