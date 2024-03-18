package com.money.system.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class InvalidTokenException extends MainException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
