package com.money.exception.auth;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class BlankTokenException extends MainException {

    public BlankTokenException() {
        super(ErrorCode.BLANK_TOKEN);
    }
}
