package com.money.system.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class InvalidPlatformException extends MainException {

    public InvalidPlatformException() {
        super(ErrorCode.INVALID_PLATFORM);
    }
}
