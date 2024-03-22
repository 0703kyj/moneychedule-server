package com.money.domain.payment.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class NotFoundWithdrawTypeException extends MainException {

    public NotFoundWithdrawTypeException() {
        super(ErrorCode.NOT_FOUND_WITHDRAW_TYPE);
    }
}
