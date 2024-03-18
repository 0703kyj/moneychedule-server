package com.money.system.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class BlankTokenException extends MainException {

    public BlankTokenException() {
        super(ErrorCode.BLANK_TOKEN);
    }
}
