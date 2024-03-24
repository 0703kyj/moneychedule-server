package com.money.domain.payment.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class NotFoundDepositTypeException extends MainException {

    public NotFoundDepositTypeException() {
        super(ErrorCode.NOT_FOUND_DEPOSIT_TYPE);
    }
}
