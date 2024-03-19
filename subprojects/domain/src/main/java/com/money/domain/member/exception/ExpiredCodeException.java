package com.money.domain.member.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class ExpiredCodeException extends MainException {

    public ExpiredCodeException() {
        super(ErrorCode.EXPIRED_CODE);
    }
}
