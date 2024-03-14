package com.money.exception.auth;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class PasswordMismatchException extends MainException {

    public PasswordMismatchException() {
        super(ErrorCode.MISMATCH_PASSWORD);
    }
}
