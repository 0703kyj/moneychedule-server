package com.money.domain.payment.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class FailPaymentServiceException extends MainException {

    public FailPaymentServiceException() {
        super(ErrorCode.FAIL_PAYMENT_SERVICE);
    }
}
