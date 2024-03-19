package com.money.exception;

public class ExpiredCodeException extends MainException{

    public ExpiredCodeException() {
        super(ErrorCode.EXPIRED_CODE);
    }
}
