package com.money.exception.auth;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class InvalidBearerException extends MainException {

    public InvalidBearerException() {
        super(ErrorCode.AUTHORIZATION_FAILED);
    }
}
