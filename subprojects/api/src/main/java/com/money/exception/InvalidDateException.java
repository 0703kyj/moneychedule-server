package com.money.exception;

public class InvalidDateException extends MainException{

    public InvalidDateException() {
        super(ErrorCode.INVALID_DATE);
    }
}
