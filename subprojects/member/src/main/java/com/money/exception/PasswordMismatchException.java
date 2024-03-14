package com.money.exception;

public class PasswordMismatchException extends MainException {

    public PasswordMismatchException() {
        super(ErrorCode.MISMATCH_PASSWORD);
    }
}
